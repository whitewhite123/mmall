package com.msj.service;

import com.msj.pojo.Cart;

import java.util.List;

public interface CartService {
    List<Cart> getCartProductList(Integer userId);
}
