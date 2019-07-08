package com.msj.common;

public enum  ResponseCode {
    //用户接口
    SUCCESS(200,"SUCCESS"),
    ERROR(1,"密码错误"),

    REGISTER_SUCCESS(0,"校验成功"),
    REGISTER_ERROR(1,"用户已存在"),

    LOGIN_SUCCESS(0,"SUCCESS"),
    LOGIN_ERROR(1,"用户未登录,无法获取当前用户信息"),

    GET_QUESTION_SUCCESS(0,"这里是问题"),
    GET_QUESTION_ERROR(1,"该用户未设置找回密码问题"),

    CHECK_ANSWER_SUCCESS(0,"531ef4b4-9663-4e6d-9a20-fb56367446a5"),
    CHECK_ANSWER_ERROR(1,"问题答案错误"),

    UPDATE_PASSWORD_SUCCESS(0,"修改密码成功"),
    UPDATE_PASSWORD_ERROR(1,"修改密码操作失效"),
    UPDATE_PASSWORD_ERROR2(1,"旧密码输入错误"),

    UPDATE_INFORMATION_SUCCESS(0,"更新个人信息成功"),
    UPDATE_INFORMATION_ERROR(1,"用户未登录"),

    LOGOUT_SUCCESS(0,"退出成功"),
    LOGOUT_ERROR(1,"服务端异常");






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
