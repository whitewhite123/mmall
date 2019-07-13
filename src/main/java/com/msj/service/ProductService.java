package com.msj.service;

import com.github.pagehelper.PageInfo;
import com.msj.pojo.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductService {
    Product selectDetail(Integer id);
    List<Product> getProductList();
    Product getProductDetail(Integer id);
    int editProductStatus(@Param("id") Integer id, @Param("status") Integer status);
    Product getProductById(Integer id);
    int addProduct(Product product);
    int editProduct(Product product);
}
