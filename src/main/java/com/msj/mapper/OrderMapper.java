package com.msj.mapper;

import com.msj.pojo.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}