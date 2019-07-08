package com.msj.common;

public enum ProductResponseCode {
    //产品接口
    PRODUCT_DETAIL_SUCCESS(0,"SUCCESS"),
    PRODUCT_DETAIL_ERROR(1,"该商品已下架或删除")

    ;
    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ProductResponseCode(int code, String desc){
        this.code = code;
        this.desc = desc;
    }

}
