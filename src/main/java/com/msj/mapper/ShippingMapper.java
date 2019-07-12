package com.msj.mapper;

import com.msj.pojo.Shipping;

public interface ShippingMapper {
    int deleteByPrimaryKey(Integer id);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}