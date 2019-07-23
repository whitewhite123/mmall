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
    User selectIs(User user);
    Integer insertOne(User user);
    User selectByType(@Param("str")String str,@Param("type")String type);
    User selectForCheck(String str);
    User getQuestion(String username);
    User checkAnswer(@Param("username")String username,@Param("question")String question);
    Integer updatePassword(@Param("username")String username,@Param("password")String password);
    Integer updateInformation(User user);
    User getInformation(Integer id);


}