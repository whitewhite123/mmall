package com.msj.service.impl;

import com.msj.mapper.ProductMapper;
import com.msj.pojo.Product;
import com.msj.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;

    public Product selectDetail(Integer id) {
        return productMapper.selectByPrimaryKey(id);
    }

    public List<Product> getProductList() {
        return productMapper.selectAll();
    }

    public Product getProductDetail(Integer id){
        return productMapper.selectByPrimaryKey(id);
    }

    public int editProductStatus(@Param("id") Integer id, @Param("status") Integer status){
        return productMapper.updateProductStatus(id,status);
    }

    public Product getProductById(Integer id){
        return productMapper.selectByPrimaryKey(id);
    }

    public int addProduct(Product product){
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        return productMapper.insertProduct(product);
    }

    public int editProduct(Product product){
        product.setUpdateTime(new Date());
        return productMapper.updateProduct(product);
    }


}
