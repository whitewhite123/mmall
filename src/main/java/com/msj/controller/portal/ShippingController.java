package com.msj.controller.portal;

import com.msj.common.ServerResponse;
import com.msj.pojo.Shipping;
import com.msj.service.ShippingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

//收获地址
@Controller
@RequestMapping("/shipping")
public class ShippingController {
    @Autowired
    private ShippingServer shippingServer;

    //添加地址
    @RequestMapping("/add.do")
    @ResponseBody
    public ServerResponse add(Shipping shipping, HttpSession session){
        return shippingServer.add(shipping,session);
    }

    //删除地址
    @RequestMapping("/del.do")
    @ResponseBody
    public ServerResponse del(Integer shippingId, HttpSession session){
        return shippingServer.del(shippingId,session);
    }

    //更新地址
    @RequestMapping("/update.do")
    @ResponseBody
    public ServerResponse update(Shipping shipping, HttpSession session){
        return shippingServer.update(shipping,session);
    }

    //查看具体的地址
    @RequestMapping("/select.do")
    @ResponseBody
    public ServerResponse select(Integer shippingId, HttpSession session){
        return shippingServer.select(shippingId,session);
    }

    //地址列表
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam("pageNum")Integer pageNum,
                               @RequestParam("pageSize")Integer pageSize,
                               HttpSession session){
        return shippingServer.list(pageNum,pageSize,session);
    }

}
