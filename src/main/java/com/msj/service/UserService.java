package com.msj.service;

import com.msj.common.ServerResponse;
import com.msj.pojo.User;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
//    门户
    ServerResponse login(String username,String password, HttpSession session);
    ServerResponse Register(User user);
    ServerResponse checkValid(String str,String type);
    ServerResponse getUserInfo(HttpSession session);
    ServerResponse getQuestion(String username);
    ServerResponse resetPassword(String passwordOld,String passwordNew,HttpSession session);
    ServerResponse checkAnswer(User user,HttpSession session);
    ServerResponse forgetResetPassword(String username,String passwordNew,String forgetToken,HttpSession session);
    ServerResponse updateInformation(User user,HttpSession session);
    ServerResponse getInformation(HttpSession session);


}
