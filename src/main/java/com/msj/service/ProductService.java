package com.msj.service;

import com.github.pagehelper.PageInfo;
import com.msj.common.ServerResponse;
import com.msj.pojo.Product;
import org.apache.ibatis.annotations.Param;

import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;
import java.util.List;

public interface ProductService {
    //门户
    ServerResponse getProductDetail(Integer id);
    ServerResponse getProductList(Integer pageNum,Integer pageSize,Integer categoryId,String orderBy);

    //后台
    ServerResponse getList(Integer pageNum,Integer pageSize,HttpSession session);
    ServerResponse search(String productName,Integer id,Integer pageNum,Integer pageSize,
                          HttpSession session);
    ServerResponse getDetail(Integer id,HttpSession session);
    ServerResponse setSaleStatus(Integer id,Integer status);



    Product getProductById(Integer id);
    int addProduct(Product product);
    int editProduct(Product product);
}
