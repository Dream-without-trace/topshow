package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-23
 **/
public enum RecommendType {

    ON("开启", 0),

    OFF("关闭", 1);

    private Integer code;

    private String value;

    RecommendType() {
    }

    RecommendType(String value, Integer code) {
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
