package com.luwei.common.enums.status;

/**
 * @author ZhangHongJie
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${todo}
 * @date 2018/12/323:26
 */
public enum MembershipCardOrderStatus {


    CREATE("创建", 0),
    CLOSE("关闭", 1),
    PAY("支付", 2),
    REFUND("退款",3);

    private Integer code;

    private String value;

    MembershipCardOrderStatus() {
    }

    MembershipCardOrderStatus(String value, Integer code) {
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
