package com.luwei.common.enums.status;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-23
 **/
public enum CollectStatus {

    YES("收藏", 0),
    NO("未收藏", 1);

    private Integer code;

    private String value;

    CollectStatus() {
    }

    CollectStatus(String value, Integer code) {
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
