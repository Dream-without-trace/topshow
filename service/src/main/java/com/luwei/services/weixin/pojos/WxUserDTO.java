package com.luwei.services.weixin.pojos;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author Leone
 * @since 2018-08-03
 **/
@Data
@ApiModel
public class WxUserDTO {

    private String openid;

    private String nickname;

    private String gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

}
