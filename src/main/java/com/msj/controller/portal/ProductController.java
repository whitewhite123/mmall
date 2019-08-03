package com.msj.controller.portal;

import com.msj.common.ServerResponse;

import com.msj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


//产品
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //产品搜素及动态排序
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(Integer categoryId, @RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize,
                               @RequestParam(value = "orderBy",defaultValue = "price_desc") String orderBy){
        return productService.getProductList(pageNum,pageSize,categoryId,orderBy);

    }

    //产品详情
    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
        return productService.getProductDetail(productId);
    }
}
