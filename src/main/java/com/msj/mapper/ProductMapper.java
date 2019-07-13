package com.msj.mapper;

import com.msj.pojo.Product;
import com.msj.pojo.ProductWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(ProductWithBLOBs record);

    int updateByPrimaryKey(Product record);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAll();

    int updateProductStatus(@Param("id") Integer id, @Param("status") Integer status);

    int insertProduct(Product product);

    int updateProduct(Product product);



}