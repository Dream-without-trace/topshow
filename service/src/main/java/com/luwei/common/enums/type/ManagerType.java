package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-08-01
 **/
public enum ManagerType {


    PLATFORM(0, "平台"), STORE(1, "店铺");

    private Integer code;

    private String value;

    ManagerType() {
    }

    ManagerType(Integer code, String value) {
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
