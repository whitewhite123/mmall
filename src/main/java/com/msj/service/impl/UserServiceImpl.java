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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

//    门户
    //登录
    public ServerResponse login(String username,String password, HttpSession session) {
        String pwd = MD5Util.MD5EncodeUtf8(password);
        User user = userMapper.selectByName(username, pwd);
        //如果取得出来user，证明已经被注册过了的，登录成功，否则登录失败
        if (user!= null) {
            session.setAttribute("user",user);
            return ServerResponse.createSuccess(user);
        }
        return ServerResponse.createByErrorCodeMessage(ResponseCode.LOGIN_ERROR.getCode(),
                ResponseCode.LOGIN_ERROR.getDesc());
    }

    //注册
    public ServerResponse Register(User user) {
        String pwd = MD5Util.MD5EncodeUtf8(user.getPassword());//加密
        User userOne = userMapper.selectIs(user);
        //先进行查询，如果不为空，则注册过；如果查询为空，则未注册，新增一条数据
        if(userOne == null){
            user.setPassword(pwd);
            user.setRole(0);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            Integer resultCount = userMapper.insertOne(user);
            if(resultCount>0){
                return ServerResponse.createByErrorMessage(Const.REGISTER_SUCCESS_MESSAGE);
            }
        }
        return  ServerResponse.createBySuccessMessage(Const.REGISTER_ERROR_MESSAGE);
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
        return null;
    }
    public ServerResponse getCheckResult(User user){
        if(user!=null){
            return ServerResponse.createByErrorMessage(Const.REGISTER_ERROR_MESSAGE);
        }
        return ServerResponse.createBySuccessMessage(Const.REGISTER_SUCCESS_MESSAGE);
    }

    public User getQuestion(String username) {
        return userMapper.getQuestion(username);
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
