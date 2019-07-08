package com.msj.service.portal;

import com.msj.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    User selectByName(@Param("username")String username,@Param("password")String password);
    User selectForRegister(@Param("username")String username,@Param("password")String password,
                           @Param("email")String email,@Param("phone")String phone,
                           @Param("question")String question,@Param("answer")String answer);
    User selectForCheck(@Param("str")String str,@Param("type")String type);
    User getQuestion(String username);
    User checkAnswer(@Param("username")String username,@Param("question")String question);
    Integer updatePassword(@Param("username")String username,@Param("password")String password);
    Integer updateInformation(User user);
}
