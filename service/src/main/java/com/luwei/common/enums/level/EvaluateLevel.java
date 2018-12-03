package com.luwei.common.enums.level;

/**
 * @author Leone
 * @since 2018-08-10
 **/
public enum EvaluateLevel {

    GOOD("好评", 0),
    MEDIUM("中评", 1),
    BAD("差评", 2);

    private Integer code;

    private String value;

    EvaluateLevel() {
    }

    EvaluateLevel(String value, Integer code) {
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
