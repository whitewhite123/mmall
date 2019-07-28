package com.msj.service.impl;

import com.google.common.base.Splitter;
import com.msj.common.Const;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.CartMapper;
import com.msj.mapper.ProductMapper;
import com.msj.pojo.Cart;
import com.msj.pojo.Product;
import com.msj.pojo.User;
import com.msj.service.CartService;
import com.msj.vo.CartProductVo;
import com.msj.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartMapper cartMapper;


    //购物车List列表
    public ServerResponse getCartProductList(HttpSession session){
        //1、判断用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、查询cartProductVo,根据userId查询个人购物车列表
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProduct(user.getId());
        //3、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //4、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList, user.getId());
        if(cartVo == null){
            return ServerResponse.createByErrorMessage(Const.CARTLIST_EMPTY);//购物车为空
        }
        return ServerResponse.createSuccess(cartVo);
    }

    //购物车添加商品
    public ServerResponse add(Integer productId, Integer count,HttpSession session){
        // 1. 判断用户是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());
        }
        // 2.判断商品是否在购物车中
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(), productId);
        if(cart == null){
            //2.1新增购物车
            ServerResponse response = insertCart(user.getId(), productId, count);
            if(response.error()){//如果新增不成功，返回错误标识
                return response;
            }
        }else{
            //2.2更新购物车
            ServerResponse response = updateCart(user.getId(), productId, count);
            if(response.error()){//如果更新不成功，返回错误标识
                return response;
            }
        }
        // 3.查询CartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProduct(user.getId());
        //4.整合CartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);

        //5.整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList,user.getId());
        return ServerResponse.createSuccess(cartVo);
    }

    //更新购物车某个产品数量
    public ServerResponse update(Integer productId,Integer count,HttpSession session) {
        //1、判断是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、更新购物车产品数量
        Cart cart = cartMapper.selectByUserIdAndProductId(user.getId(), productId);
        cart.setQuantity(count);
        cart.setUpdateTime(new Date());
        int resultCount = cartMapper.updateByPrimaryKeySelective(cart);
        if(resultCount < 0){//更新不成功，返回错误标识
            return ServerResponse.createByErrorMessage(Const.UPDATE_PRODUCT_FAIL);
        };
        //3、查询cartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProduct(user.getId());
        //4、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //5、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList,user.getId());
        return ServerResponse.createSuccess(cartVo);
    }

    //移出某个商品
    public ServerResponse delete(String productIds,HttpSession session){
        //1、用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、删除购物车商品
        List<String> productIdList = Splitter.on(",").splitToList(productIds);
        for(String pid:productIdList){
            Integer productId = Integer.valueOf(pid);
            Integer resultCount = cartMapper.deleteByProductId(productId);
            if(resultCount < 0){
                return ServerResponse.createBySuccessMessage(Const.DELETE_PRODUCT_FAIL);
            }
        }

        //3、查询cartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProduct(user.getId());
        //4、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //5、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList, user.getId());
        return ServerResponse.createSuccess(cartVo);
    }

    //购物车选中某个商品
    public ServerResponse select(Integer productId,HttpSession session){
        //1、用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、选中商品，更新checked=1
        Date updateTime = new Date();
        int resultCount = cartMapper.updateChecked1ByPidAndUid(productId, user.getId(),updateTime);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.SELETE_PRODUCT_FAIL);
        }
        //3、查询cartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProductByUidAndPid(user.getId(), productId);
        //4、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //5、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList,user.getId());
        return ServerResponse.createSuccess(cartVo);
    }

    //取消选中的商品
    public ServerResponse unSelect(Integer productId,HttpSession session){
        //1、用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、选中商品，更新checked=0
        Date updateTime = new Date();
        int resultCount = cartMapper.updateChecked0ByPidAndUid(productId, user.getId(),updateTime);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.SELETE_PRODUCT_FAIL);
        }
        //3、查询cartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProductByUidAndPid(user.getId(), productId);
        //4、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //5、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList,user.getId());
        return ServerResponse.createSuccess(cartVo);
    }

    //查询在购物车里的产品数量
    public ServerResponse getCartProductCount(HttpSession session) {
        //1、用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、查看购物车产品数量
        Integer resultCount = cartMapper.selectCartProductCount(user.getId());
        if(resultCount<0){
            return ServerResponse.createByErrorMessage(Const.CARTLIST_EMPTY);//购物车为空
        }
        return ServerResponse.createSuccess(resultCount);
    }

    //购物车全选
    public ServerResponse selectAll(HttpSession session){
        //1、用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、全选商品，更新checked=1
        Date updateTime = new Date();
         int resultCount = cartMapper.updateChecked1ByUid(user.getId(),updateTime);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.SELETE_PRODUCT_FAIL);
        }
        //3、查询cartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProduct(user.getId());
        //4、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //5、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList,user.getId());
        return ServerResponse.createSuccess(cartVo);
    }

    //购物车取消全选
    public ServerResponse unSelectAll(HttpSession session){
        //1、用户是否登录
        User user = (User)session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());//用户未登录
        }
        //2、取消全选，更新checked=0
        Date updateTime = new Date();
        int resultCount = cartMapper.updateChecked0ByUid(user.getId(),updateTime);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.SELETE_PRODUCT_FAIL);
        }
        //3、查询cartProductVo
        List<CartProductVo> cartProductVoList = cartMapper.selectCartProduct(user.getId());
        //4、整合cartProductVo
        cartProductVoList = assembleCartProductVoList(cartProductVoList);
        //5、整合cartVo
        CartVo cartVo = assembleCartVo(cartProductVoList,user.getId());
        return ServerResponse.createSuccess(cartVo);
    }




    //整合cartVo
    private CartVo assembleCartVo(List<CartProductVo> cartProductVoList,Integer userId){
        CartVo cartVo = new CartVo();
        cartVo.setCartProductVoList(cartProductVoList);
        Integer resultCount = cartMapper.selectCheckByUserId(userId);//根据check=0（没选中）查询
        //如果有选中的就是true
        if(resultCount > 0){
            cartVo.setAllChecked(false);
        }
        cartVo.setAllChecked(true);
        Double totalPrice = cartMapper.selectCartTotalPriceByUserId(userId);
        cartVo.setCartTotalPrice(totalPrice);
        return cartVo;
    }

     //整合CartProductVoList
    private List<CartProductVo> assembleCartProductVoList(List<CartProductVo> cartProductVoList){
        for (CartProductVo cartProductVoItem : cartProductVoList){
            double productPrice = cartProductVoItem.getProductPrice();
            int quantity = cartProductVoItem.getQuantity();
            cartProductVoItem.setProductTotalPrice(quantity*productPrice);
            //判断购买数量是否大于库存
            if(quantity > cartProductVoItem.getProductStock()){
                cartProductVoItem.setLimitQuantity(Const.LIMIT_QUANTITY_FAIL);
            }else{
                cartProductVoItem.setLimitQuantity(Const.LIMIT_QUANTITY_SUCCESS);
            }
        }
        return cartProductVoList;
    }

    //更新购物车
    private ServerResponse updateCart(Integer userId,Integer productId,Integer count){
        Cart cart = cartMapper.selectByUserIdAndProductId(userId, productId);
        cart.setQuantity(cart.getQuantity()+count);
        cart.setUpdateTime(new Date());
        int resultCount = cartMapper.updateByPrimaryKeySelective(cart);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.UPDATE_PRODUCT_FAIL);
        }
        return ServerResponse.createBySuccessMessage(Const.UPDATE_PRODUCT_SUCCESS);
    }

    //新增购物车
    private ServerResponse insertCart(Integer userId,Integer productId,Integer count){
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(productId);
        cart.setQuantity(count);
        cart.setChecked(1);
        cart.setCreateTime(new Date());
        cart.setUpdateTime(new Date());
        int resultCount= cartMapper.insertCart(cart);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.ADD_PRODUCT_FAIL);
        }
        return ServerResponse.createSuccess();
    }


}
