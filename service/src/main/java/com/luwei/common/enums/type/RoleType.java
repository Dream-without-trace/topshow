package com.luwei.common.enums.type;

public enum RoleType {

    /*普通管理员*/
    ROOT("超级管理员", 0),

    /**超级管理员*/
    ADMIN("普通管理员", 1),

    /*商家管理员*/
    STORE("商家管理员", 2),

    /*商家管理员*/
    USER("普通用户", 3),

    /*访客*/
    VISITOR("访客", 4);

    private Integer code;

    private String value;

    RoleType() {
    }

    RoleType(String value, Integer code) {
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
