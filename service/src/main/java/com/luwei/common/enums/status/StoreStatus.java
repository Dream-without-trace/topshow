package com.luwei.common.enums.status;

/**
 * @author Leone
 * @since 2018-08-03
 **/
public enum StoreStatus {

    ON("上架", 0),
    OFF("下架", 1);

    private Integer code;

    private String value;

    StoreStatus() {
    }

    StoreStatus(String value, Integer code) {
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
