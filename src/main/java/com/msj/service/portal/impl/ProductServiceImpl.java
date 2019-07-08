package com.msj.service.portal.impl;

import com.msj.mapper.ProductMapper;
import com.msj.pojo.Product;
import com.msj.service.portal.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    public Product selectDetail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }
}
