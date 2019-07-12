package com.msj.mapper;

import com.msj.pojo.PayInfo;

public interface PayInfoMapper {
    int deleteByPrimaryKey(Integer id);

    PayInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}