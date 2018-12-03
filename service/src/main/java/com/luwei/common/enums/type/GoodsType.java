package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-03
 **/
public enum GoodsType {

    PRODUCT("普通商品", 0),
    RECOMMEND("推荐商品", 1);

    private Integer code;

    private String value;

    GoodsType() {
    }

    GoodsType(String value, Integer code) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }


}
