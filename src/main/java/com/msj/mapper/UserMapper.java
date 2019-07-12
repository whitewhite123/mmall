package com.msj.mapper;

import com.msj.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

//  门户
    User selectByName(@Param("username")String username,@Param("password")String password);
    User selectForRegister(User user);
    User selectForCheck(String str);
    User getQuestion(String username);
    User checkAnswer(@Param("username")String username,@Param("question")String question);
    Integer updatePassword(@Param("username")String username,@Param("password")String password);
    Integer updateInformation(User user);

//   用户
    List<User> selectUserList();
}