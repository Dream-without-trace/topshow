package com.luwei.common.enums.status;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public enum ActivityStatus {

    NOT_START("未开始", 0),
    UNDERWAY("进行中", 1),
    OVER("结束", 2);

    private Integer code;

    private String value;

    ActivityStatus() {
    }

    ActivityStatus(String value, Integer code) {
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
