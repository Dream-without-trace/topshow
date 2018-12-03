package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-02
 **/
public enum ActivityType {

    RECOMMEND("强力推荐", 0),
    HOT("城中热事", 1),
    LIKE("猜你喜欢", 2);

    private Integer code;

    private String value;

    ActivityType() {
    }

    ActivityType(String value, Integer code) {
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
