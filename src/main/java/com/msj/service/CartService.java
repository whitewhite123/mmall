package com.msj.service;

import com.msj.common.ServerResponse;
import com.msj.pojo.Cart;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CartService {
    ServerResponse getCartProductList(HttpSession session);
    ServerResponse add(Integer productId,Integer count,HttpSession session);
}
