package com.luwei.common.enums.status;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public enum ActivityOrderStatus {

    CREATE("创建", 0),
    CLOSE("关闭", 1),
    PAY("支付", 2),
    JOIN("参加", 3),
    COMPLETE("完成", 4),
    REFUND("退款", 5);

    private Integer code;

    private String value;

    ActivityOrderStatus() {
    }

    ActivityOrderStatus(String value, Integer code) {
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
