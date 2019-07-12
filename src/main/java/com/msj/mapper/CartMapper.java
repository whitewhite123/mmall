package com.msj.mapper;

import com.msj.pojo.Cart;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);
}