package com.luwei.common.enums.type;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-20
 **/
public enum TicketType {

    FREE("免费票", 0),
    VIP("VIP票", 1);

    private Integer code;

    private String value;

    TicketType() {
    }

    TicketType(String value, Integer code) {
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
