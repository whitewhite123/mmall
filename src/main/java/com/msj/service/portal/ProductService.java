package com.msj.service.portal;

import com.github.pagehelper.PageInfo;
import com.msj.pojo.Product;

import java.util.List;

public interface ProductService {
    Product selectDetail(Integer id);
    PageInfo<Product> getProductList(Integer pageNum, Integer pageSize);
}
