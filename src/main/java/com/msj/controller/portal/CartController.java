package com.msj.controller.portal;

import com.msj.common.Const;
import com.msj.common.ServerResponse;
import com.msj.pojo.Cart;
import com.msj.pojo.User;
import com.msj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session){
        //todo 查出购物车的总额，字段名不同
        User user = (User)session.getAttribute("user");
        Integer userId = user.getId();
        System.out.println(userId);
        if(user!=null){
            List<Cart> cartProductList = cartService.getCartProductList(userId);
            System.out.println(cartProductList);
            return ServerResponse.createSuccess(cartProductList);
        }
        return ServerResponse.createByErrorMessage(Const.UPDATE_INFORMATION_ERROR);
    }
}

