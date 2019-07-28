package com.msj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.msj.common.Const;
import com.msj.common.ResponseCode;
import com.msj.common.ServerResponse;
import com.msj.mapper.ShippingMapper;
import com.msj.pojo.Shipping;
import com.msj.pojo.User;
import com.msj.service.ShippingServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShippingServerImpl implements ShippingServer{
    @Autowired
    private ShippingMapper shippingMapper;

    //添加收获地址
    public ServerResponse add(Shipping shipping, HttpSession session) {
        //1、判断是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());
        }
        //2、添加地址
        shipping.setUserId(user.getId());
        shipping.setCreateTime(new Date());
        shipping.setUpdateTime(new Date());
        int restultCount = shippingMapper.insertShipping(shipping);
        //3、取出自增长的id,然后放在一个Hashmap对象中
        Integer shippingId = shipping.getId();
        Map<String, Integer> ship = new HashMap<String, Integer>();
        ship.put("shippingId",shippingId);
        if(restultCount < 0){
            return ServerResponse.createByErrorMessage(Const.ADD_ADDRESS_FAIL);//新增地址失败
        }
        return ServerResponse.createBySuccess(Const.ADD_ADDRESS_SUCCESS,ship);//新增地址成功
    }

    //删除地址
    public ServerResponse del(Integer shippingId, HttpSession session) {
        //1、判断是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());
        }
        //2、根据shippingId去删除
        int resultCount = shippingMapper.deleteByShippingId(shippingId);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.DEL_ADDRESS_FAIL);//删除地址失败
        }
        return ServerResponse.createByErrorMessage(Const.DEL_ADDRESS_SUCCESS);//删除地址成功
    }

    //登录状态更新地址
    public ServerResponse update(Shipping shipping, HttpSession session) {
        //1、判断是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorCodeMessage(ResponseCode.GETINFORMATION_ERROR.getCode(),
                    ResponseCode.GETINFORMATION_ERROR.getDesc());
        }
        //2、根据id先查出来地址
        Shipping shippingDetail = shippingMapper.selectDetailAddressById(shipping.getId());
        if(shippingDetail == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_ADDRESS_FAIL);
        }
        shipping.setUpdateTime(new Date());
        int resultCount = shippingMapper.updateShipping(shipping);
        if(resultCount < 0){
            return ServerResponse.createByErrorMessage(Const.UPDATE_ADDRESS_FAIL);
        }
        return ServerResponse.createBySuccessMessage(Const.UPDATE_ADDRESS_SUCCESS);
    }

    //选中查看具体的地址
    public ServerResponse select(Integer shippingId, HttpSession session) {
        //1、判断是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN);//请登录之后查询
        }
        //2、根据shippingId查看具体地址
        Shipping shipping = shippingMapper.selectDetailAddressById(shippingId);
        if(shipping == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN);
        }
        return ServerResponse.createSuccess(shipping);
    }

    //地址列表
    public ServerResponse list(Integer pageNum, Integer pageSize, HttpSession session) {
        //1、判断是否登录
        User user = (User) session.getAttribute("user");
        if(user == null){
            return ServerResponse.createByErrorMessage(Const.NEED_LOGIN);//请登录之后查询
        }
        List<Shipping> shippingList = shippingMapper.selectAddressByUserId(user.getId());
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<Shipping> shippingPageInfo = new PageInfo<Shipping>(shippingList);
        if(shippingPageInfo == null){
            return ServerResponse.createByErrorMessage(Const.SELECT_ADDRESS_FAIL);
        }
        return ServerResponse.createSuccess(shippingPageInfo);
    }

}
