package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public enum AgeType {

    UNKNOWN("未知", 0),
    ZERO_THREE("0-3岁", 1),
    FOUR_SEVEN("4-7岁", 2),
    EIGHT_ELEVEN("8-11岁", 3),
    ELEVEN_FIFTEEN("11-15岁", 4);

    private Integer code;

    private String value;

    AgeType() {
    }

    AgeType(String value, Integer code) {
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
