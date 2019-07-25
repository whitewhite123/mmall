package com.msj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.Const;
import com.msj.common.ManageConst;
import com.msj.common.ServerResponse;
import com.msj.mapper.ProductMapper;
import com.msj.pojo.Product;
import com.msj.pojo.User;
import com.msj.service.ProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductMapper productMapper;
//门户
    //产品搜索及动态排序List
    public ServerResponse getProductList(Integer pageNum,Integer pageSize,
                                         Integer categoryId,String orderBy) {
        PageHelper.startPage(pageNum,pageSize);
        List<Product> productList = productMapper.selectByCategoryId(categoryId,orderBy);
        PageInfo<Product> productPageInfo = new PageInfo<Product>(productList);
        if(productPageInfo!=null){
            return ServerResponse.createSuccess(productPageInfo);
        }
        return ServerResponse.createByErrorMessage(ManageConst.ARGUMENT_ERROR);//参数错误
    }

    //产品详情
    public ServerResponse getProductDetail(Integer id) {
        Product product = productMapper.selectByPrimaryKey(id);
        if(product!=null){
            return ServerResponse.createSuccess(product);
        }
        return ServerResponse.createByErrorMessage(Const.PRODUCT_DETAIL_ERROR);//该商品已下架或删除
    }

//后台
    //产品list
    public ServerResponse getList(Integer pageNum, Integer pageSize, HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user!=null){
            PageHelper.startPage(pageNum,pageSize);
            List<Product> productList = productMapper.selectAllProducts();
            PageInfo<Product> productPageInfo = new PageInfo<Product>(productList);
            return ServerResponse.createSuccess(productPageInfo);

        }
        return ServerResponse.createByErrorMessage(ManageConst.GETPRODUCT_ERROR);//用户未登录，请先登录
    }

    //产品搜素
    public ServerResponse search(String productName,Integer id, Integer pageNum,
                                 Integer pageSize,HttpSession session) {
        if(session.getAttribute("user")!=null){
            List<Product> productList = productMapper.selectByType(productName,id);
            if(productList!=null){
                PageHelper.startPage(pageNum,pageSize);
                PageInfo<Product> productPageInfo = new PageInfo<Product>(productList);
                return ServerResponse.createSuccess(productPageInfo);
            }
        }

        return ServerResponse.createByErrorMessage(ManageConst.GETPRODUCT_ERROR);//用户未登录
    }

    //产品详情
    public ServerResponse getDetail(Integer id, HttpSession session) {
        if(session.getAttribute("user")!=null){
            Product product = productMapper.selectByPrimaryKey(id);
            if(product!=null){
                return ServerResponse.createSuccess(product);
            }
        }
        return ServerResponse.createByErrorMessage(ManageConst.GETLIST_ERROR);
    }

    //产品上下架
    public ServerResponse setSaleStatus(@Param("id") Integer id, @Param("status") Integer status){
        int resultCount = productMapper.updateProductStatus(id,status);
        if(resultCount>0){
            return ServerResponse.createBySuccessMessage(ManageConst.UPDATE_STATUS_SUCCESS);//修改产品状态成功
        }
        return ServerResponse.createByErrorMessage(ManageConst.UPDATE_STATUS_ERROR);//修改产品状态失败
    }

    public Product getProductById(Integer id){
        return productMapper.selectByPrimaryKey(id);
    }

    public int addProduct(Product product){
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        return productMapper.insertProduct(product);
    }

    public int editProduct(Product product){
        product.setUpdateTime(new Date());
        return productMapper.updateProduct(product);
    }


}
