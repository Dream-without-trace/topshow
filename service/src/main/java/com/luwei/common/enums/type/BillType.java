package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public enum BillType {

    INCOME("收入", 0),

    EXPEND("支出", 1);

    private Integer code;

    private String value;

    BillType() {
    }

    BillType(String value, Integer code) {
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
