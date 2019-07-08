package com.msj.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.web.bind.annotation.ResponseBody;

@JsonInclude(JsonInclude.Include.NON_NULL)//保证序列化Json的时候，如果值为null，会不显示这个key
public class ServerResponse {

    private int status;
    private String msg;
    private Object data;

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    /**
     * 构造函数
     * @param status 状态码
     * @param data 对象
     */
    private ServerResponse(int status, Object data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 构造函数
     * @param status 状态码
     * @param msg
     */
    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }


    /**
     * 登录成功
     * @param data 自定义data
     * @return
     */
    public static ServerResponse createBySuccess(Object data){
        return new ServerResponse(ResponseCode.SUCCESS.getCode(),data);
    }

    /**
     * 登录失败
     * @param
     * @return
     */
    public static ServerResponse createByFailMessage(){
        return new ServerResponse(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }

    public static ServerResponse loginBySuccess(Object data){
        return new ServerResponse(ResponseCode.LOGIN_SUCCESS.getCode(),data);
    }


    public static ServerResponse loginByFail(){
        return new ServerResponse(ResponseCode.LOGIN_ERROR.getCode(),
                ResponseCode.LOGIN_ERROR.getDesc());
    }
    /**
     * 注册成功
     * @param
     * @return
     */
    public static ServerResponse registerBySuccess(){
        return new ServerResponse(ResponseCode.REGISTER_SUCCESS.getCode(),
                ResponseCode.REGISTER_SUCCESS.getDesc());
    }
    /**
     * 注册成功
     * @param
     * @return
     */
    public static ServerResponse registerByFail(){
        return new ServerResponse(ResponseCode.REGISTER_ERROR.getCode(),
                ResponseCode.REGISTER_ERROR.getDesc());
    }

    /**
     * 找回密码的问题成功
     * @return
     */
    public static ServerResponse getQuestionSuccess(){
        return new ServerResponse(ResponseCode.GET_QUESTION_SUCCESS.getCode(),
                ResponseCode.GET_QUESTION_SUCCESS.getDesc());
    }
    /**
     * 找回密码的问题失败
     * @return
     */
    public static ServerResponse getQuestionFail(){
        return new ServerResponse(ResponseCode.GET_QUESTION_ERROR.getCode(),
                ResponseCode.GET_QUESTION_ERROR.getDesc());
    }

    /**
     * 提交问题答案
     * @return
     */
    public static ServerResponse checkAnswerSuccess(){
        return new ServerResponse(ResponseCode.CHECK_ANSWER_SUCCESS.getCode(),
                ResponseCode.CHECK_ANSWER_SUCCESS.getDesc());
    }
    public static ServerResponse checkAnswerFail(){
        return new ServerResponse(ResponseCode.CHECK_ANSWER_ERROR.getCode(),
                ResponseCode.CHECK_ANSWER_ERROR.getDesc());
    }

    /**
     * 重设密码
     * @return
     */
    public static ServerResponse updatePasswordSuccess(){
        return new ServerResponse(ResponseCode.UPDATE_PASSWORD_SUCCESS.getCode(),
                ResponseCode.UPDATE_PASSWORD_SUCCESS.getDesc());
    }
    public static ServerResponse updatePasswordFail(){
        return new ServerResponse(ResponseCode.UPDATE_PASSWORD_ERROR.getCode(),
                ResponseCode.UPDATE_PASSWORD_ERROR.getDesc());
    }
    public static ServerResponse updatePasswordFail2(){
        return new ServerResponse(ResponseCode.UPDATE_PASSWORD_ERROR2.getCode(),
                ResponseCode.UPDATE_PASSWORD_ERROR2.getDesc());
    }

    /**
     * 更新个人信息
     * @return
     */
    public static ServerResponse updateInformationSuccess(){
        return new ServerResponse(ResponseCode.UPDATE_INFORMATION_SUCCESS.getCode(),
                ResponseCode.UPDATE_INFORMATION_SUCCESS.getDesc());
    }
    public static ServerResponse updateInformationFail(){
        return new ServerResponse(ResponseCode.UPDATE_INFORMATION_ERROR.getCode(),
                ResponseCode.UPDATE_INFORMATION_ERROR.getDesc());
    }

    /**
     * 退出登录
     * @return
     */
    public static ServerResponse logoutSuccess(){
        return new ServerResponse(ResponseCode.LOGOUT_SUCCESS.getCode(),
                ResponseCode.LOGOUT_SUCCESS.getDesc());
    }
    public static ServerResponse logoutFail(){
        return new ServerResponse(ResponseCode.LOGOUT_ERROR.getCode(),
                ResponseCode.LOGOUT_ERROR.getDesc());
    }
}
