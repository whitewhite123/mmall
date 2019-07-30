package com.msj.service;

import com.msj.common.ServerResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

public interface OrderService {
    ServerResponse create(Integer shippingId, HttpSession session);
    ServerResponse getOrderCartProduct(HttpSession session);
    ServerResponse getOrderList(HttpSession session,Integer pageSize,Integer pageNum);
    ServerResponse detail(HttpSession session,BigInteger orderNo);
    ServerResponse cancel(HttpSession session,BigInteger orderNo);

    ServerResponse search(HttpSession session,BigInteger orderNo);
    ServerResponse sendGoods(HttpSession session,BigInteger orderNo);


    ServerResponse pay(HttpSession session,BigInteger orderNo,HttpServletRequest request);
    ServerResponse queryOrderPayStatus(HttpSession session,BigInteger orderNo);
    ServerResponse alipayCallback(HttpSession session, HttpServletRequest request);


}
