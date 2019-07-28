package com.msj.mapper;

import com.msj.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;


public interface OrderItemMapper {

    OrderItem selectOrderItemByUidAndOrderNo(@Param("userId")Integer userId,@Param("orderNo") Long orderNo);

    int deleteByPrimaryKey(Integer id);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}