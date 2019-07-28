package com.msj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.Const;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.OrderItemMapper;
import com.msj.mapper.OrderMapper;
import com.msj.pojo.Order;
import com.msj.pojo.OrderItem;
import com.msj.pojo.User;
import com.msj.service.OrderServer;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class OrderServerImpl implements OrderServer{
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    
    //创建订单
    public ServerResponse create(Integer shippingId, HttpSession session) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }

        return null;
    }

    //获取订单的商品信息
    public ServerResponse getOrderCartProduct(HttpSession session) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }
        return null;
    }

    //获取订单的商品信息
    public ServerResponse getOrder(HttpSession session,Integer pageSize,Integer pageNum) {
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.NEED_LOGIN_ERROR.getDesc());//用户未登录
        }

        //2、根据userId查询order
        List<Order> orderList = orderMapper.selectOrderByUserId(user.getId());
        //3、整合order
        orderList = assembleOrder(orderList, user.getId(),user.getUsername());
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Order> pageInfo = new PageInfo<Order>(orderList);
        return ServerResponse.createSuccess(pageInfo);
    }

    //整合order
    private List<Order> assembleOrder(List<Order> orderList, Integer userId,String username){
        for(Order o:orderList){
            OrderItem orderItem = orderItemMapper.selectOrderItemByUidAndOrderNo(userId,o.getOrderNo());
            o.setOrderItem(orderItem);
            //支付类型
            String paymentTypeDesc = (String)Const.payType.get(o.getPaymentType());
            o.setPaymentTypeDesc( paymentTypeDesc);
            //支付状态
            String statusDesc = (String)Const.status.get(o.getStatus());
            o.setStatusDesc(statusDesc);
            o.setReceiveName(username);
        }
        return orderList;
    }
}
