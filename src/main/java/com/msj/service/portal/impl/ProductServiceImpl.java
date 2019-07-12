package com.msj.service.portal.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.mapper.ProductMapper;
import com.msj.pojo.Product;
import com.msj.service.portal.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;

    public Product selectDetail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public PageInfo<Product> getProductList(Integer pageNum,Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products = productMapper.selectAll();
        PageInfo<Product> productPageInfo = new PageInfo<Product>(products);
        System.out.println(productPageInfo);
        return productPageInfo;
    }


}
