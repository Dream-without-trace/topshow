package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-26
 **/
public enum DealType {

    INCOME("收入", 0),
    EXPENDITURE("支出", 1);

    private Integer code;

    private String value;

    DealType() {
    }

    DealType(String value, Integer code) {
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
