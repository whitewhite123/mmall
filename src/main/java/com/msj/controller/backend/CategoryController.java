package com.msj.controller.backend;

import com.msj.common.ServerResponse;
import com.msj.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

//品类接口
@RestController
@RequestMapping("/manage/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;


    //获取品类子节点(平级)
    @RequestMapping("/get_category.do")
    public ServerResponse getCategory(HttpSession session,
            @RequestParam(value = "categoryId",defaultValue = "0",required = false) Integer categoryId){
        return categoryService.getCategory(session,categoryId);
    }

    //增加节点
    @RequestMapping("/add_category.do")
    public ServerResponse addCategory(HttpSession session,
          @RequestParam(value = "parentId",defaultValue = "0") Integer parentId,
          String categoryName){
         return categoryService.addCategory(session,parentId,categoryName);
    }

    //修改品类名字
    @RequestMapping("/set_category_name.do")
    public ServerResponse setCategoryName(HttpSession session,
           @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId,
           String categoryName){
        return categoryService.setCategoryName(session,categoryId,categoryName);
    }

    //获取当前分类id及递归子节点categoryId
    @RequestMapping("/get_deep_category.do")
    public ServerResponse getDeepCategory(HttpSession session,
           @RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        return categoryService.getDeepCategory(session,categoryId);
    }
}
