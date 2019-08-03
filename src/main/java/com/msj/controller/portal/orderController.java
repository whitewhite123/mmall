package com.msj.controller.portal;
import com.msj.common.ServerResponse;
import com.msj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;

//订单
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    //创建订单
    @RequestMapping("/create.do")
    @ResponseBody
    public ServerResponse create(Integer shippingId, HttpSession session){
        return orderService.create(shippingId,session);
    }

    //获取订单的商品信息
    @RequestMapping("/get_order_cart_product.do")
    @ResponseBody
    public ServerResponse getOrderCartProduct(HttpSession session){
        return orderService.getOrderCartProduct(session);
    }

    //获取订单List
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse getOrderList(HttpSession session,
                                       @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        return orderService.getOrderList(session,pageSize,pageNum);
    }

    //获取订单详情detail
    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse detail(HttpSession session,BigInteger orderNo){
        return orderService.detail(session,orderNo);
    }

    //取消订单
    @RequestMapping("/cancel.do")
    @ResponseBody
    public ServerResponse cancel(HttpSession session,BigInteger orderNo){
        return orderService.cancel(session,orderNo);
    }





    //支付
    @RequestMapping("/pay.do")
    @ResponseBody
    public ServerResponse pay(HttpSession session,BigInteger orderNo,HttpServletRequest request){
        return orderService.pay(session,orderNo,request);
    }

    //查询订单支付状态
    @RequestMapping("/query_order_pay_status.do")
    @ResponseBody
    public ServerResponse queryOrderPayStatus(HttpSession session,BigInteger  orderNo){
        return orderService.queryOrderPayStatus(session,orderNo);
    }

    //支付宝回调
    @RequestMapping("/alipay_callback.do")
    @ResponseBody
    public String alipayCallback(HttpServletRequest request){
        return orderService.alipayCallback(request);
    }
}

