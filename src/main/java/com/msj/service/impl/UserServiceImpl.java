package com.msj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.Const;
import com.msj.common.ManageConst;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.UserMapper;
import com.msj.pojo.User;
import com.msj.service.UserService;
import com.msj.util.MD5Util;
import com.mysql.fabric.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

//   门户(用户接口)
    //登录
    public ServerResponse login(String username, String password, HttpSession session) {
        String pwd = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectByName(username, pwd);
        //如果取得出来user，证明已经被注册过了的，登录成功，否则登录失败
        if (user != null) {
            session.setAttribute("user", user); //设置session
            return ServerResponse.createSuccess(user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.LOGIN_ERROR.getCode(),
                ResponseCode.LOGIN_ERROR.getDesc());
    }

    //注册
    public ServerResponse Register(User user) {
        String pwd = MD5Util.MD5EncodeUtf8(user.getPassword());//加密
        user.setPassword(pwd);
        System.out.println(user);
        String usernameValue = user.getUsername();
        String emailValue = user.getEmail();
        String phoneValue = user.getPhone();
        //检查用户是否存在，如果存在该用户，返回错误码;满足任何一条都为true，证明用户已注册
        if (checkValid(usernameValue, "username").error() ||
                checkValid(emailValue, "email").error() ||
                checkValid(phoneValue, "phone").error()) {
            //用户已存在
            return ServerResponse.createByErrorMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setRole(0);
        Integer resultCount = userMapper.insertOne(user);
        if (resultCount > 0) {
            //注册成功，校验成功
            return ServerResponse.createBySuccessMessage(Const.REGISTER_SUCCESS_MESSAGE);
        }
        return ServerResponse.createByErrorMessage(Const.REGISTER_ILLEGAL_MESSAGE);

//        //先进行查询，如果不为空，则注册过；如果查询为空，则未注册，新增一条数据
//        if(userOne == null){
//            user.setPassword(pwd);
//            user.setRole(0);
//            user.setCreateTime(new Date());
//            user.setUpdateTime(new Date());
//            Integer resultCount = userMapper.insertOne(user);
//            if(resultCount>0){
//                return ServerResponse.createByErrorMessage(Const.REGISTER_SUCCESS_MESSAGE);
//            }
//        }
//        return  ServerResponse.createBySuccessMessage(Const.REGISTER_ERROR_MESSAGE);
    }

    //检查用户名是否有效
    public ServerResponse checkValid(String str, String type) {
        if ("username".equals(type)) {
            User user = userMapper.selectByType(str, type);
            return getCheckResult(user);
        } else if ("email".equals(type)) {
            User user = userMapper.selectByType(str, type);
            return getCheckResult(user);
        } else if ("phone".equals(type)) {
            User user = userMapper.selectByType(str, type);
            return getCheckResult(user);
        }
        //参数不合法
        return ServerResponse.createByErrorMessage(Const.illegalArgument);
    }

    public ServerResponse getCheckResult(User user) {
        if (user != null) {
            //如果不为空，说明有这一行，用户已存在
            return ServerResponse.createByErrorMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        //为空，校验成功
        return ServerResponse.createBySuccessMessage(Const.REGISTER_SUCCESS_MESSAGE);
    }

    //获取登录用户信息
    public ServerResponse getUserInfo(HttpSession session) {
        if (session.getAttribute("user") != null) {
            //session不为空，说明已经登录了，可以取得用户信息
            return ServerResponse.createSuccess(session.getAttribute("user"));
        }
        return ServerResponse.createByErrorMessage(Const.GETINFORMATION_ERROR_MESSAGE);
    }

    //忘记密码--获取问题
    public ServerResponse getQuestion(String username) {
        User user = userMapper.getQuestion(username);
        //判断用户是否存在
        if (user != null) {
            String question = user.getQuestion();
            //判断问题是否为空
            if (question.equals("")) {
                return ServerResponse.createByErrorMessage(Const.GETQUESTION_ERROR);//该用户未设置找回密码问题
            }
            return ServerResponse.createBySuccessMessage(Const.GETQUESTION_SUCCESS);//这里是问题
        }
        return ServerResponse.createByErrorMessage(Const.GETQUESTION_ILLEGAL);//该用户未注册

    }

    //登录状态--重设密码
    public ServerResponse resetPassword(String passwordOld, String passwordNew, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        User userInformation = userMapper.getInformation(id);

        String pwdOld = MD5Util.MD5EncodeUtf8(passwordOld);
        String pwdNew = MD5Util.MD5EncodeUtf8(passwordNew);
        //判断旧密码输入是否正确
        if ((userInformation.getPassword()).equals(pwdOld)) {
            Integer resultCount = userMapper.updatePassword(user.getUsername(), pwdNew);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage(Const.UPDATE_PASSWORD_SUCCESS);//修改密码成功
            }
        }
        return ServerResponse.createByErrorMessage(Const.UPDATE_PASSWORD_ERROR2);//旧密码输入错误
    }

    //提交问题答案
    public ServerResponse checkAnswer(User user, HttpSession session) {
        User userOne = userMapper.checkAnswer(user.getUsername(), user.getQuestion());
        if (userOne != null) {
            if ((userOne.getAnswer()).equals(user.getAnswer())) {
                String token = UUID.randomUUID().toString();
                ServerResponse serverResponse = ServerResponse.createSuccess(token);
                session.setAttribute("token", token);
                return serverResponse;
            }
            return ServerResponse.createByErrorMessage(Const.CHECKEANSWER_ERROR);//问题答案错误
        }
        return ServerResponse.createByErrorMessage(Const.GETQUESTION_ILLEGAL);//用户未注册
    }

    //忘记密码--重设密码
    public ServerResponse forgetResetPassword(String username, String passwordNew, String forgetToken, HttpSession session) {
        System.out.println(session.getAttribute("token"));
        if ((session.getAttribute("token")).equals(forgetToken)) {
            String pwdNew = MD5Util.MD5EncodeUtf8(passwordNew);
            Integer resultCount = userMapper.updatePassword(username, pwdNew);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage(Const.UPDATE_PASSWORD_SUCCESS);//修改密码成功
            }
            return ServerResponse.createByErrorMessage(Const.UPDATE_PASSWORD_ERROR);//修改密码操作失效
        }
        return ServerResponse.createByErrorMessage(Const.ILLEGAL_TOKEN);//token已经失效
    }

    //登录状态更新个人信息
    public ServerResponse updateInformation(User user, HttpSession session) {
        //如果session取出来不为空，就进行判断，为空说明未登录
        User userOne = (User) session.getAttribute("user");
        //userOne不为空，说明已经登录了
        if (userOne != null) {
            userOne.setUpdateTime(new Date());
            userOne.setEmail(user.getEmail());
            userOne.setPhone(user.getPhone());
            userOne.setQuestion(user.getQuestion());
            userOne.setAnswer(user.getAnswer());
            System.out.println(userOne);
            Integer resultCount = userMapper.updateInformation(userOne);
            System.out.println(resultCount);
            if (resultCount > 0) {
                return ServerResponse.createBySuccessMessage(Const.UPDATE_INFORMATION_SUCCESS);//更新个人信息成功
            }
        }
        return ServerResponse.createByErrorMessage(Const.UPDATE_INFORMATION_ERROR);//用户未登录
    }

    //获取当前登录用户的详细信息，并强制登录
    public ServerResponse getInformation(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Integer id = user.getId();
        User UserInformation = userMapper.getInformation(id);
        UserInformation.setPassword("");
        if (UserInformation != null) {
            return ServerResponse.createSuccess(UserInformation);//显示用户详细信息
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                ResponseCode.GETINFORMATION_ERROR.getDesc());
    }

    //退出登录
    public ServerResponse logout(HttpSession session) {
        if(session.getAttribute("user")!=null){
            //设置session为空
            session.setAttribute("user",null);
            return ServerResponse.createBySuccessMessage(Const.LOGOUT_SUCCESS);//退出成功
        }
        return ServerResponse.createByErrorMessage(Const.LOGOUT_ERROR);//服务端异常
    }


//    后台（用户接口）
    //查看用户列表
    public ServerResponse getList(Integer pageSize, Integer pageNum, HttpSession session) {
        User manageUser = (User) session.getAttribute("user");
        //manageUser不为空，说明登录成功
        if (manageUser != null) {
            //判断角色：role=1管理员，role=0用户
            if(manageUser.getRole() == 1){
                PageHelper.startPage(pageNum, pageSize);//页数，页大小
                Integer userRole = 0;
                List<User> userList = userMapper.selectUserList(userRole);
                PageInfo<User> pageInfo = new PageInfo<User>(userList);
                return ServerResponse.createSuccess(pageInfo);
            }
            //如果role不为1（管理员），说明没有权限
            return ServerResponse.createByErrorMessage(ManageConst.GETLIST_ERROR);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.MANAGE_LOGIN_ERROR.getCode(),
                ResponseCode.MANAGE_LOGIN_ERROR.getDesc());//用户未登录,请登录

    }
}
