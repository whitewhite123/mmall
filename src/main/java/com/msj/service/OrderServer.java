package com.msj.service;

import com.msj.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface OrderServer {
    ServerResponse create(Integer shippingId, HttpSession session);
    ServerResponse getOrderCartProduct(HttpSession session);
    ServerResponse getOrder(HttpSession session,Integer pageSize,Integer pageNum);
}
