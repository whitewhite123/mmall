package com.msj.service;

import com.msj.common.ServerResponse;
import com.msj.pojo.Cart;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CartService {
    ServerResponse getCartProductList(HttpSession session);
    ServerResponse add(Integer productId,Integer count,HttpSession session);
    ServerResponse update(Integer productId,Integer count,HttpSession session);
    ServerResponse delete(String productIds,HttpSession session);
    ServerResponse select(Integer productId,HttpSession session);
    ServerResponse unSelect(Integer productId,HttpSession session);
    ServerResponse getCartProductCount(HttpSession session);
    ServerResponse selectAll(HttpSession session);
    ServerResponse unSelectAll(HttpSession session);
}
