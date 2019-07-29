package com.msj.mapper;

import com.msj.pojo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;


public interface OrderItemMapper {

    List<OrderItem> selectOrderItemByUidAndOrderNo(@Param("userId")Integer userId, @Param("orderNo")BigInteger orderNo);

    int selectCountByUidAndOrderNo(@Param("userId")Integer userId, @Param("orderNo")BigInteger orderNo);

    List<OrderItem> selectOrderItemByUserId(Integer userId);

    Double selectProductTotalPrice(Integer userId);

    int addOrderItem(OrderItem orderItem);


    int deleteByPrimaryKey(Integer id);

    OrderItem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderItem record);

    int updateByPrimaryKey(OrderItem record);
}