package com.msj.common;

import com.fasterxml.jackson.core.util.InternCache;

import java.util.HashMap;
import java.util.Map;

public class  Const {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 1;

    //MD5加盐
    public static final String code = "salt";

    //参数错误
    public static final String illegalArgument = "参数不合法";

/* 门户  */
// 用户接口
    public static final String REGISTER_SUCCESS_MESSAGE = "校验成功";
    public static final String REGISTER_ERROR_MESSAGE = "用户已存在";
    public static final String REGISTER_ILLEGAL_MESSAGE = "注册信息不合法";

    public static final String GETINFORMATION_ERROR_MESSAGE = "用户未登录,无法获取当前用户信息";

    public static final String GETQUESTION_SUCCESS = "这里是问题";
    public static final String GETQUESTION_ERROR = "该用户未设置找回密码问题";
    public static final String GETQUESTION_ILLEGAL = "该用户未注册";

    public static final String CHECKEANSWER_ERROR = "问题答案错误";

    public static final String UPDATE_PASSWORD_SUCCESS = "修改密码成功";
    public static final String UPDATE_PASSWORD_ERROR = "修改密码操作失效";
    public static final String ILLEGAL_TOKEN = "token已经失效";
    public static final String UPDATE_PASSWORD_ERROR2 = "旧密码输入错误";

    public static final String UPDATE_INFORMATION_SUCCESS = "更新个人信息成功";
    public static final String NEED_LOGIN_ERROR = "用户未登录";

    public static final String LOGOUT_SUCCESS = "退出成功";
    public static final String LOGOUT_ERROR = "服务端异常";


//    产品接口
    public static final String PRODUCT_DETAIL_ERROR = "该商品已下架或删除";

//    购物车接口
    public static final String LIMIT_QUANTITY_SUCCESS = "LIMIT_NUM_SUCCESS";
    public static final String LIMIT_QUANTITY_FAIL = "LIMIT_NUM_FAIL";
    public static final String ADD_PRODUCT_SUCCESS="成功加入购物车";
    public static final String ADD_PRODUCT_FAIL = "新增购物车失败";
    public static final String UPDATE_PRODUCT_SUCCESS="成功更新购物车";
    public static final String UPDATE_PRODUCT_FAIL = "更新购物车失败";
    public static final String CARTLIST_EMPTY= "购物车为空";
    public static final String DELETE_PRODUCT_FAIL= "商品移出失败";
    public static final String SELETE_PRODUCT_FAIL= "选中商品失败";

    //支付类型
    public static final Map payType = new HashMap<Integer,String>();

    //订单状态
    public static final String CREATE_ORDER_ERROR = "创建订单失败";
    public static final String CREATE_ORDERITEM_ERROR = "创建订单详情失败";
    public static final Map status = new HashMap<Integer,String>();


    static{
        payType.put(1,"在线支付");
        status.put(0,"已取消");
        status.put(10,"未付款");
        status.put(20,"已付款");
        status.put(40,"已发货");
        status.put(50,"交易成功");
        status.put(60,"交易关闭");
    }

//   收获地址接口
    public static final String ADD_ADDRESS_SUCCESS = "新建地址成功";
    public static final String ADD_ADDRESS_FAIL = "新建地址失败";
    public static final String DEL_ADDRESS_SUCCESS = "删除地址成功";
    public static final String DEL_ADDRESS_FAIL = "删除地址失败";
    public static final String NEED_LOGIN = "请登录之后查询";
    public static final String SELECT_ADDRESS_FAIL = "查询地址失败";
    public static final String UPDATE_ADDRESS_FAIL = "更新地址失败";
    public static final String UPDATE_ADDRESS_SUCCESS = "更新地址成功";

//    订单接口
    public static final String SEL_ORDER_ERROR = "没有订单，无法查询订单信息";

//    支付接口
    public static final String SELECT_ORDER_ERROR = "没有找到订单";
    public static final String PAY_ORDER_ERROR = "支付宝生成订单失败";
    public static final String NO_ORDER = "该用户没有此订单";
    public static final String CANCEL_ORDER_ERROR = "此订单已付款，无法被取消";
    public static final String CANCEL_ERROR = "订单取消失败";
    public static final String CANCEL_ORDER_SUCCESS = "成功取消订单";
}
