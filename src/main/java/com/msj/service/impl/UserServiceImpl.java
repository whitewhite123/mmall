package com.msj.service.impl;

import com.msj.common.Const;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.UserMapper;
import com.msj.pojo.User;
import com.msj.service.UserService;
import com.msj.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.rmi.ServerError;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

//    门户(用户接口)
    //登录
    public ServerResponse login(String username,String password, HttpSession session) {
        String pwd = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectByName(username, pwd);
        //如果取得出来user，证明已经被注册过了的，登录成功，否则登录失败
        if (user!= null) {
            session.setAttribute("user",user); //设置session
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
        if(checkValid(usernameValue,"username").error()|| 
                checkValid(emailValue,"email").error()||
                checkValid(phoneValue,"phone").error()){
            //用户已存在
            return ServerResponse.createByErrorMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        user.setRole(0);
        Integer resultCount = userMapper.insertOne(user);
        if(resultCount>0){
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
        if("username".equals(type)){
            User user = userMapper.selectByType(str,type);
            return getCheckResult(user);
        }
        else if("email".equals(type)){
            User user = userMapper.selectByType(str,type);
            return getCheckResult(user);
        }
        else if("phone".equals(type)){
            User user = userMapper.selectByType(str,type);
            return getCheckResult(user);
        }
        //参数不合法
        return ServerResponse.createByErrorMessage(Const.illegalArgument);
    }
    public ServerResponse getCheckResult(User user){
        if(user!=null){
            //如果不为空，说明有这一行，用户已存在
            return ServerResponse.createByErrorMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        //为空，校验成功
        return ServerResponse.createBySuccessMessage(Const.REGISTER_SUCCESS_MESSAGE);
    }

    //获取登录用户信息
    public ServerResponse getUserInfo(HttpSession session){
        if(session.getAttribute("user")!=null){
            //session不为空，说明已经登录了，可以取得用户信息
            return ServerResponse.createSuccess(session.getAttribute("user"));
        }
        return ServerResponse.createByErrorMessage(Const.GETINFORMATION_ERROR_MESSAGE);
    }

    //忘记密码--获取问题
    public ServerResponse getQuestion(String username) {
        User user = userMapper.getQuestion(username);
        //判断用户是否存在
        if(user!=null){
            String question = user.getQuestion();
            //判断问题是否为空
            if(question.equals("")){
                return ServerResponse.createByErrorMessage(Const.GETQUESTION_ERROR);//该用户未设置找回密码问题
            }
            return ServerResponse.createBySuccessMessage(Const.GETQUESTION_SUCCESS);//这里是问题
        }
        return ServerResponse.createByErrorMessage(Const.GETQUESTION_ILLEGAL);//该用户未注册

    }

    public User getAnswer(String username, String question) {
        return userMapper.checkAnswer(username,question);
    }

    public Integer getUpdatePassword(String username, String password) {
       return userMapper.updatePassword(username,password);
    }

    public Integer getUpdateInformation(User user) {
        return userMapper.updateInformation(user);
    }

//后台
    public List<User> getUserList(){
        return userMapper.selectUserList();
    }
}
