package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.common.enums.type.RoleType;
import com.luwei.common.utils.RandomUtil;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.services.manager.cms.LoginSuccessVO;
import com.luwei.services.user.UserService;
import com.luwei.services.user.address.web.UserAddressVO;
import com.luwei.services.user.address.web.UserReceivingAddDTO;
import com.luwei.services.user.web.UserInfoAddDTO;
import com.luwei.services.user.web.UserInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Leone
 * @since Monday September
 **/
@Slf4j
@RestController
@Api(tags = "用户模块")
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private ShiroTokenService shiroTokenService;

    @Resource
    private UserService userService;


    @ApiOperation("绑定手机号")
    @PutMapping("/binding")
    public Response<String> binding(@RequestParam String phone, @RequestParam Integer userId, @RequestParam String captcha) {
        if (userService.binding(phone, userId, captcha)) {
            return Response.success("绑定成功");
        } else {
            return Response.error("绑定失败");
        }
    }

    @PutMapping
    @ApiOperation("完善个人资料")
    public void editUser(@RequestBody @Valid UserInfoAddDTO dto) {
        userService.addUserInfo(dto);
    }

    @PutMapping("/receiving")
    @ApiOperation("添加收货地址")
    public UserAddressVO addReceivingAddress(@RequestBody @Valid UserReceivingAddDTO dto) {
        return userService.addReceivingAddress(dto);
    }

    @GetMapping("/integral/total")
    @ApiOperation("获取某个用户积分总和")
    public Response<Integer> findOne(@RequestParam Integer userId) {
        Integer result = userService.findIntegralTotal(userId);
        return Response.build(20000, "success", result);
    }

    @GetMapping
    @ApiOperation("用户详细信息")
    public UserInfoVO findUser(@RequestParam Integer userId) {
        return userService.userInfo(userId);
    }

    @GetMapping("/address")
    @ApiOperation("用户收货地址信息")
    public UserAddressVO userAddress(@RequestParam Integer userId) {
        return userService.userAddress(userId);
    }

}
