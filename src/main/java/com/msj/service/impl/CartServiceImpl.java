package com.msj.service.impl;

import com.msj.common.Const;
import com.msj.common.ServerResponse;
import com.msj.mapper.CartMapper;
import com.msj.pojo.Cart;
import com.msj.pojo.User;
import com.msj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartMapper cartMapper;

    //购物车List列表
    public ServerResponse getCartProductList(HttpSession session){
        User user = (User)session.getAttribute("user");
        Integer userId = user.getId();
        if(user!=null){
            List<Cart> cartProductList = cartMapper.selectProductByUserId(userId);
            return ServerResponse.createSuccess(cartProductList);
        }
        return ServerResponse.createByErrorMessage(Const.NEED_LOGIN_ERROR);//用户未登录
    }
}
