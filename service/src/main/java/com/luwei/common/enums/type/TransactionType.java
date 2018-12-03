package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-09-07
 **/
public enum TransactionType {

    ACTIVITY("活动交易", 0),

    GOODS("商品交易", 1),
    CARD("办理会员交易", 2);

    private Integer code;

    private String value;

    TransactionType() {
    }

    TransactionType(String value, Integer code) {
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
