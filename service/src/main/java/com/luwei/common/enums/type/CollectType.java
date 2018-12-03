package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public enum  CollectType {

    GOODS("商品", 0),
    ACTIVITY("活动", 1);

    private Integer code;

    private String value;

    CollectType() {
    }

    CollectType(String value, Integer code) {
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
