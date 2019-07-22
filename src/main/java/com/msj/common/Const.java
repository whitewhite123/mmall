package com.msj.common;

public class Const {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 1;

    //MD5加盐
    public static final String code = "salt";

    //参数错误
    public static final String illegalArgument = "参数不合法";

//    用户接口
    public static final String REGISTER_SUCCESS_MESSAGE = "校验成功";
    public static final String REGISTER_ERROR_MESSAGE = "用户已存在";
    public static final String REGISTER_ILLEGAL_MESSAGE = "注册信息不合法";

    public static final String GETINFORMATION_ERROR_MESSAGE = "用户未登录,无法获取当前用户信息";

    public static final String GETQUESTION_SUCCESS = "这里是问题";
    public static final String GETQUESTION_ERROR = "该用户未设置找回密码问题";
    public static final String GETQUESTION_ILLEGAL = "该用户未注册";

    public static final String CHECKANSWER_SUCCESS = "531ef4b4-9663-4e6d-9a20-fb56367446a5";
    public static final String CHECKEANSWER_ERROR = "问题答案错误";

    public static final String UPDATE_PASSWORD_SUCCESS = "修改密码成功";
    public static final String UPDATE_PASSWORD_ERROR = "修改密码操作失效";
    public static final String UPDATE_PASSWORD_ERROR2 = "旧密码输入错误";

    public static final String UPDATE_INFORMATION_SUCCESS = "更新个人信息成功";
    public static final String UPDATE_INFORMATION_ERROR = "用户未登录";

    public static final String LOGOUT_SUCCESS = "退出成功";
    public static final String LOGOUT_ERROR = "服务端异常";


//    产品接口
    public static final String PRODUCT_DETAIL_ERROR = "该商品已下架或删除";
}
