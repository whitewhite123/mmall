package com.msj.controller.portal;

import com.msj.common.Const;
import com.msj.common.ServerResponse;
import com.msj.pojo.Product;
import com.msj.service.portal.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId){
        Product product = productService.selectDetail(productId);
        if(product!=null){
            return ServerResponse.createSuccess(product);
        }
        return ServerResponse.createErrorByMessage(Const.PRODUCT_DETAIL_ERROR);
    }
}
