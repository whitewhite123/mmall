package com.msj.service.portal.impl;

import com.msj.mapper.UserMapper;
import com.msj.pojo.User;
import com.msj.service.portal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    public User getLogin(String username, String password) {
        return userMapper.selectByName(username, password);
    }

    public User getRegister(User user) {
        return userMapper.selectForRegister(user);
    }


    public User getCheck(String str, String type) {
        if("username".equals(type)){
            return userMapper.selectForCheck(str);
        }
        return null;
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


}
