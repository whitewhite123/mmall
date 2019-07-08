package com.msj.service.portal.impl;

import com.msj.mapper.UserMapper;
import com.msj.pojo.User;
import com.msj.service.portal.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    public User selectByName(String username, String password) {
        return userMapper.selectByName(username,password);
    }

    public User selectForRegister(String username, String password, String email, String phone, String question, String answer) {
        return userMapper.selectForRegister(username,password,email,phone,question,answer);
    }


    public User selectForCheck(String str, String type) {
        if("username".equals(type)){
            return userMapper.selectForCheck(str);
        }
        return null;
    }

    public User getQuestion(String username) {
        return userMapper.getQuestion(username);
    }

    public User checkAnswer(String username, String question) {
        return userMapper.checkAnswer(username,question);
    }

    public Integer updatePassword(String username, String password) {
       return userMapper.updatePassword(username,password);
    }

    public Integer updateInformation(User user) {
        return userMapper.updateInformation(user);
    }


}
