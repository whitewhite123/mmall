package com.msj.common;

public enum ResponseCode {
    //用户接口
    LOGIN_SUCCESS(200,"SUCCESS"),
    LOGIN_ERROR(1,"密码错误");


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
