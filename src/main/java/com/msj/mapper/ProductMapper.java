package com.msj.mapper;

import com.msj.pojo.Product;
import com.msj.pojo.ProductWithBLOBs;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    List<Product> selectByCategoryId(@Param("categoryId") Integer categoryId,@Param("orderBy") String orderBy);

    Product selectByPrimaryKey(Integer id);

    List<Product> selectAllProducts();

    List<Product> selectByType(@Param("productName")String productName,@Param("id")Integer id);

    int updateProductStatus(@Param("id") Integer id, @Param("status") Integer status);

    int insertProduct(Product product);

    int updateByPrimaryKeySelective(Product product);




}