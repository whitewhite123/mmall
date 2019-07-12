package com.msj.service.portal;

import com.msj.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserService {
    User getLogin(@Param("username")String username,@Param("password")String password);
    User getRegister(User user);
    User getCheck(@Param("str")String str,@Param("type")String type);
    User getQuestion(String username);
    User getAnswer(@Param("username")String username,@Param("question")String question);
    Integer getUpdatePassword(@Param("username")String username,@Param("password")String password);
    Integer getUpdateInformation(User user);
}
