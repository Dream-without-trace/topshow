package com.luwei.common.enums.type;

/**
 * @author Leone
 * @since 2018-09-01
 **/
public enum SortType {

    DESC("降序", 0),
    ASC("升序", 1);

    private Integer code;

    private String value;

    SortType() {
    }

    SortType(String value, Integer code) {
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
