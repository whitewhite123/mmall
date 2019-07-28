package com.msj.mapper;

import com.msj.pojo.Order;
import com.msj.pojo.OrderItem;

import java.util.List;

public interface OrderMapper {


    List<Order> selectOrderByUserId(Integer userId);

    int deleteByPrimaryKey(Integer id);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}