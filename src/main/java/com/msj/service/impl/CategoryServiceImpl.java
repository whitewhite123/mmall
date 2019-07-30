package com.msj.service.impl;

import com.msj.common.Const;
import com.msj.common.ServerResponse;
import com.msj.mapper.CategoryMapper;
import com.msj.pojo.Category;
import com.msj.pojo.User;
import com.msj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;


    //获取品类子节点(平级)
    public ServerResponse getCategory(HttpSession session, Integer categoryId) {
        //1、判断是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN_ERROR);
        }
        if(categoryId == 0){//默认category=0
            categoryId = null;
        }
        //2、根据categoryId和parentId查询
        Integer parentId = 0;
        List<Category> categoryList = categoryMapper.selectByCategoryId(categoryId,parentId);
        if(categoryList == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_CATEGORY_ERROR);//未找到该品类
        }
        return ServerResponse.createSuccess(categoryList);
    }

    //增加节点
    public ServerResponse  addCategory(HttpSession session, Integer parentId, String categoryName) {
        //1、判断是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN_ERROR);//未登录
        }
        //2、得到category
        Category category = gainCategory(parentId, categoryName);
        int resultCount = categoryMapper.insertByPidAndCname(category);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.ADD_CATEGORY_ERROR);//添加失败
        }
        return ServerResponse.createByErrorMessage(Const.ADD_CATEGORY_SUCCESS);
    }

    //得到category
    private Category gainCategory(Integer parentId, String categoryName){
        Category c = new Category();
        c.setParentId(parentId);
        c.setName(categoryName);
        c.setStatus(1);
        c.setCreateTime(new Date());
        c.setUpdateTime(new Date());
        return c;
    }

    //修改品类名字
    public ServerResponse setCategoryName(HttpSession session, Integer categoryId, String categoryName) {
        //1、判断是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN_ERROR);
        }
        //2、根据catrgoryId查询
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if(category == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_CATEGORY_ERROR);
        }
        int resultCount = categoryMapper.updateByCidAndCname(categoryId,categoryName);
        if(resultCount <0){
            return ServerResponse.createByErrorMessage(Const.UPDATE_CATEGORY_ERROR);//更新品类名字失败
        }
        return ServerResponse.createBySuccessMessage(Const.UPDATE_CATEGORY_SUCCESS);//更新品类名字成功
    }

    //获取当前分类id及递归子节点categoryId
    public ServerResponse getDeepCategory(HttpSession session, Integer categoryId) {
        //1、判断是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN_ERROR);
        }
        Integer parentId = categoryId;
        //2、根据parentId查询
        List<Category> categoryList = categoryMapper.selectByParentId(parentId);

        List<Category> resultList = new ArrayList<Category>();
        for (Category item : categoryList) {
            List<Category> categoryItemList = this.getCategoryId(item);
            for (Category categoryItem : categoryItemList){
                resultList.add(categoryItem);
            }
        }
        List<Integer> idList = new ArrayList<Integer>();
        for(Category c:resultList){
            idList.add(c.getId());
        }
        if(idList == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_CATEGORY_ERROR);//未找到该品类
        }
        return ServerResponse.createSuccess(idList);
    }

    //获取这个category下的所有categoryList(包含父)
    private List<Category> getCategoryId(Category category) {
        List<Category> list = new ArrayList<Category>();
        list.add(category);//添加父category
        //根据父categoryId查出下一层的categoryList
        List<Category> sonCategoryList = categoryMapper.selectByParentId(category.getId());
        for(Category  sonCategory : sonCategoryList){
            //获取这个category下的所有categoryList(包含父)
            List<Category> sonCList = getCategoryId(sonCategory);
            for (Category sonitem : sonCList){
                list.add(sonitem);
            }
        }
        return list;
    }
}
