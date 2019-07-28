package com.msj.mapper;

import com.msj.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShippingMapper {
    int insertShipping(Shipping shipping);

    int deleteByShippingId(Integer id);

    Shipping selectDetailAddressById(Integer id);

    List<Shipping> selectAddressByUserId(Integer userId);

    int updateShipping(Shipping shipping);


    int deleteByPrimaryKey(Integer id);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}