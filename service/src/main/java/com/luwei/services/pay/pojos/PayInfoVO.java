package com.luwei.services.pay.pojos;

import lombok.Data;
import lombok.ToString;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-06
 **/
@Data
@ToString
public class PayInfoVO {

    private String appId;

    private String nonceStr;

    private String packages;

    private String paySign;

    private String timeStamp;

}
