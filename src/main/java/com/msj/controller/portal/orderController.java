package com.msj.controller.portal;
import com.msj.common.ServerResponse;
import com.msj.service.OrderServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//订单
@Controller
@RequestMapping("/order")
public class orderController {
    @Autowired
    private OrderServer orderServer;

    //创建订单
    @RequestMapping("/create.do")
    @ResponseBody
    public ServerResponse create(Integer shippingId, HttpSession session){
        return orderServer.create(shippingId,session);
    }

    //获取订单的商品信息
    @RequestMapping("/get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpSession session){
        return orderServer.getOrderCartProduct(session);
    }

    //获取订单List
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse getOrderList(HttpSession session,
                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        return orderServer.getOrder(session,pageSize,pageNum);
    }
}
