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


    //成功
    /**
     * @param data 自定义data
     * @return
     */
    public static ServerResponse createSuccess(Object data){
        return new ServerResponse(Const.SUCCESS_CODE,data);
    }

    /**
     *
     * @param msg  自定义消息
     * @return
            */
    public static ServerResponse createSuccessByMessage(String msg){
        return new ServerResponse(Const.SUCCESS_CODE,msg);
    }


    //失败
    /**
     *
     * @param msg 自定义消息
     * @return
     */
    public static ServerResponse createErrorByMessage(String msg){
        return new ServerResponse(Const.ERROR_CODE,msg);
    }

    /**
     *
     * @param status 自定义状态码
     * @param msg  自定义消息
     * @return
     */
        public static ServerResponse createErrorByCodeMessage(Integer status,String msg){
            return new ServerResponse(status,msg);
    }

}
