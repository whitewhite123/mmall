package com.msj.service;

import com.msj.common.ServerResponse;
import com.msj.pojo.Shipping;

import javax.servlet.http.HttpSession;

public interface ShippingServer {
    ServerResponse add(Shipping shipping, HttpSession session);
    ServerResponse del(Integer shippingId, HttpSession session);
    ServerResponse update(Shipping shipping, HttpSession session);
    ServerResponse select(Integer shippingId, HttpSession session);
    ServerResponse list(Integer pageNum,Integer pageSize, HttpSession session);
}
