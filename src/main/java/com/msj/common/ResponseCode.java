package com.msj.common;

//枚举类
public enum ResponseCode {
    //用户接口
    LOGIN_SUCCESS(200,"SUCCESS"),
    LOGIN_ERROR(1,"密码错误"),

    GETINFORMATION_ERROR(10,"用户未登录,无法获取当前用户信息,status=10,强制登录");


    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
