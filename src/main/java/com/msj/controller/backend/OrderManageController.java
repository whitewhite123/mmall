package com.msj.controller.backend;

import com.msj.common.ServerResponse;
import com.msj.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.math.BigInteger;

@RestController
@RequestMapping("/manage/order")
public class OrderManageController {
    @Autowired
    private OrderService orderService;
    //订单List
    @RequestMapping("/list.do")
    public ServerResponse list(HttpSession session,
                               @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                               @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum){
        return orderService.getOrderList(session,pageSize,pageNum);
    }

    //按订单号查询
    @RequestMapping("/search.do")
    public ServerResponse search(HttpSession session, BigInteger orderNo){
        return orderService.search(session,orderNo);
    }

    //订单详情
    @RequestMapping("/detail.do")
    public ServerResponse detail(HttpSession session, BigInteger orderNo){
        return orderService.detail(session,orderNo);
    }

    //订单发货
    @RequestMapping("/send_goods.do")
    public ServerResponse sendGoods(HttpSession session, BigInteger orderNo){
        return orderService.sendGoods(session,orderNo);
    }
}
