package com.msj.mapper;

import com.msj.pojo.Cart;
import com.msj.vo.CartProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CartMapper {
    int deleteByPrimaryKey(Integer id);

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart cart);

    Cart selectByUserIdAndProductId(@Param("userId") Integer userId,@Param("productId") Integer productId);

    Integer selectCheckByUserId(Integer userId);

    Double selectCartTotalPriceByUserId(Integer userId);

    List<CartProductVo> selectCartProduct(Integer userId);

    int deleteByProductId(Integer productId);

    int insertCart(Cart cart);

    int updateByPrimaryKey(Integer id);

    List<Cart> selectProductByUserId(Integer userId);

}