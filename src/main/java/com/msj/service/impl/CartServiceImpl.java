package com.msj.service.impl;

import com.msj.mapper.CartMapper;
import com.msj.pojo.Cart;
import com.msj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    public List<Cart> getCartProductList(Integer userId){
        return cartMapper.selectProductByUserId(userId);
    }
}
