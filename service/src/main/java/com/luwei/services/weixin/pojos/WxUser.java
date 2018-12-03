package com.luwei.services.weixin.pojos;

import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-13
 **/
@Data
public class WxUser {

    private String openid;

    private String nickName;

    private String gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

}
