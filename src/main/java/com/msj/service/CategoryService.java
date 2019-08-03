package com.msj.service;

import com.msj.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface CategoryService {
    ServerResponse getCategory(HttpSession session,Integer categoryId);
    ServerResponse addCategory(HttpSession session,Integer parentId,String categoryName);
    ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName);
    ServerResponse getDeepCategory(HttpSession session,Integer categoryId);
}
