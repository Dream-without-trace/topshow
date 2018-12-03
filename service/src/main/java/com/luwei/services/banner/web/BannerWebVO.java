package com.luwei.services.banner.web;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-09-03
 **/
@Data
@ApiModel
public class BannerWebVO {

    private Integer bannerId;

    private String name;

    private Integer id;

    private String picture;

}
