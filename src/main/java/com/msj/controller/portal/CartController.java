package com.msj.controller.portal;
import com.msj.common.ServerResponse;
import com.msj.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//购物车
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    //购物车List列表
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(HttpSession session){
        return cartService.getCartProductList(session);
    }

    //购物车添加商品
    @RequestMapping("/add.do")
    @ResponseBody
    public ServerResponse add(Integer productId,Integer count,HttpSession session){
        return cartService.add(productId,count,session);
    }

    //购物车添加商品
    @RequestMapping("/update.do")
    @ResponseBody
    public ServerResponse update(Integer productId,Integer count,HttpSession session){
        return cartService.update(productId,count,session);
    }

    //移出某个商品
    @RequestMapping("/delete_product.do")
    @ResponseBody
    public ServerResponse deleteProduct(String productIds,HttpSession session){
        return cartService.delete(productIds,session);
    }

    //购物车选中某个商品
    @RequestMapping("/select.do")
    @ResponseBody
    public ServerResponse select(Integer productId,HttpSession session){
        return cartService.select(productId,session);
    }

    //取消购物车选中某个商品
    @RequestMapping("/un_select.do")
    @ResponseBody
    public ServerResponse unSelect(Integer productId,HttpSession session){
        return cartService.unSelect(productId,session);
    }

    //查询在购物车里的产品数量
    @RequestMapping("/get_cart_product_count.do")
    @ResponseBody
    public ServerResponse getCartProductCount(HttpSession session){
        return cartService.getCartProductCount(session);
    }

    //购物车全选
    @RequestMapping("/select_all.do")
    @ResponseBody
    public ServerResponse selectAll(HttpSession session){
        return cartService.selectAll(session);
    }

    //购物车取消全选
    @RequestMapping("/un_select_all.do")
    @ResponseBody
    public ServerResponse unSelectAll(HttpSession session){
        return cartService.unSelectAll(session);
    }
}

