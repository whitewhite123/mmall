package com.msj.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.msj.pojo.Product;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductServerResponse {
    private Integer status;
    private String msg;
    private Object data;

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }

    public ProductServerResponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ProductServerResponse(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    /**
     * 产品详情
     * @param data
     * @return
     */
    public static ProductServerResponse productDetailSuccess(Object data){
        return new ProductServerResponse(ProductResponseCode.PRODUCT_DETAIL_SUCCESS.getCode(),data);
    }
    public static ProductServerResponse productDetailFail(){
        return new ProductServerResponse(ProductResponseCode.PRODUCT_DETAIL_ERROR.getCode(),
                ProductResponseCode.PRODUCT_DETAIL_ERROR.getDesc());
    }
}
