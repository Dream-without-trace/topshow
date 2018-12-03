package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-01
 **/
public enum SexType {

    UNKNOWN("未知", 0),
    MAN("男", 1),
    WOMAN("女", 2);

    private Integer code;

    private String value;

    SexType() {
    }

    SexType(String value, Integer code) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
