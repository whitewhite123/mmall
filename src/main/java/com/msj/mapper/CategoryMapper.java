package com.msj.mapper;

import com.msj.pojo.Category;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}