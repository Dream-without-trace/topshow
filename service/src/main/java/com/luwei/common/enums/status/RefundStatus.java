package com.luwei.common.enums.status;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-20
 **/
public enum RefundStatus {

    APPLY("申请", 0),
    REFUSE("拒绝", 1),
    AGREE("同意", 2);

    private Integer code;

    private String value;

    RefundStatus() {
    }

    RefundStatus(String value, Integer code) {
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
