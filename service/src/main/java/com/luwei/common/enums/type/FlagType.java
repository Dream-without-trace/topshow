package com.luwei.common.enums.type;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-23
 **/
public enum FlagType {

    DENY("否(false)", 0),
    RIGHT("是(true)", 1);

    private Integer code;

    private String value;

    FlagType() {
    }

    FlagType(String value, Integer code) {
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
