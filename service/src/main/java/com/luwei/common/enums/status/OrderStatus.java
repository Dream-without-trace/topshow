package com.luwei.common.enums.status;

/**
 * @author Leone
 * @since 2018-08-03
 **/
public enum OrderStatus {

    CREATE("创建", 0),
    CANCEL("取消", 1),
    PAY("支付", 2),
    DELIVER("发货", 3),
    RECEIVING("收货(待评价)", 4),
    COMPLETE("完成", 5),
    REFUND("退款", 6);

    private Integer code;

    private String value;

    OrderStatus() {
    }

    OrderStatus(String value, Integer code) {
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
