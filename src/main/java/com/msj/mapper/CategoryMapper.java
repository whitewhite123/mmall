package com.msj.mapper;

import com.msj.pojo.Category;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CategoryMapper {
    List<Category> selectByCategoryId(@Param("id") Integer id,@Param("parentId")Integer parentId);

    int insertByPidAndCname(Category category);

    Category selectByPrimaryKey(Integer id);

    int updateByCidAndCname(@Param("id") Integer id,@Param("name")String categoryName);

    List<Category> selectByParentId(Integer parentId);



    int deleteByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}