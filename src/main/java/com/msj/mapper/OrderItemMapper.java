package com.msj.mapper;

import com.msj.pojo.OrderItem;

public interface OrderItemMapper {
    int deleteByPrimaryKey(Integer id);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}