package com.msj.controller.backend;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.ManageConst;
import com.msj.common.ServerResponse;
import com.msj.pojo.Product;
import com.msj.pojo.User;
import com.msj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private ProductService productService;

    //产品list
    @RequestMapping("/list.do")
    @ResponseBody
    public ServerResponse list(@RequestParam(value = "pageNum",defaultValue ="1")Integer pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                               HttpSession session){
        return productService.getList(pageNum,pageSize,session);
    }

    //产品搜素
    @RequestMapping("/search.do")
    @ResponseBody
    public ServerResponse search(@RequestParam(value = "productName",required = false)String productName,
                                 @RequestParam(value = "productId",required = false) Integer productId,
                                 @RequestParam(value = "pageNum",defaultValue ="1")Integer pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                 HttpSession session){
        return productService.search(productName,productId,pageNum,pageSize,session);
    }

    @RequestMapping("/upload.do")
    @ResponseBody
    public ServerResponse upload(){
        //todo 图片上传
        return null;
    }

    //产品详情
    @RequestMapping("/detail.do")
    @ResponseBody
    public ServerResponse detail(Integer productId,HttpSession session){
        return productService.getDetail(productId,session);
    }

    //产品上下架
    @RequestMapping("/set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(Integer productId,Integer status){
        return productService.setSaleStatus(productId,status);
    }

    //新增或者更新产品
    @RequestMapping("/save.do")
    @ResponseBody
    public ServerResponse save(Product product){
        return productService.save(product);


    }

}
