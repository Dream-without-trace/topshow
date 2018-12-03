package com.luwei.services.pay.pojos;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <p>
 *
 * @author Leone
 * @since Friday September
 **/
@Data
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class NotifyDTO {

    private String appid;

    private String mch_id;

    private String nonce_str;

    private String sign;

    private String result_code;

    private String openid;

    private String trade_type;

    private String bank_type;

    private String total_fee;

    private String transaction_id;

    private String out_trade_no;

    private String time_end;

    private String is_subscribe;

    private String return_code;

    private String fee_type;

    private String cash_fee;
}
