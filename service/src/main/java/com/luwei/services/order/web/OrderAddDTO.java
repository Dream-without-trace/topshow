package com.luwei.services.order.web;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @author Leone
 * @since 2018-08-10
 **/
@Data
@ApiModel
public class OrderAddDTO {

    @NotNull
    @ApiModelProperty("用户id")
    private Integer userId;

    @NotEmpty
    @ApiModelProperty("购物车id")
    private Set<Integer> shoppingIds;


}
