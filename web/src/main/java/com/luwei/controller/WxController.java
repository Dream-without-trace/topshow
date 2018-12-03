package com.luwei.controller;

import com.luwei.common.Response;
import com.luwei.module.shiro.service.ShiroTokenService;
import com.luwei.services.user.web.UserWebVO;
import com.luwei.services.weixin.WxService;
import com.luwei.services.weixin.pojos.OpenidVO;
import com.luwei.services.weixin.pojos.PhoneNumDTO;
import com.luwei.services.weixin.pojos.WxUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@Slf4j
@RestController
@Api(tags = "微信模块")
@RequestMapping("/api/wx")
public class WxController {

    @Resource
    private WxService wxService;

    @Resource
    private ShiroTokenService shiroTokenService;

    @ApiOperation(value = "获取openid和session_key")
    @GetMapping(value = "/openid")
    public OpenidVO openid(@RequestParam(value = "code") String code) {
        return wxService.openid(code);
    }


    @ApiOperation(value = "登录")
    @PostMapping(value = "/login")
    public UserWebVO login(@RequestBody WxUserDTO dto) {
        return wxService.login(dto);
    }

    @ApiOperation(value = "微信绑定手机号码")
    @PutMapping(value = "/binding")
    public Response bingingPhone(@RequestBody PhoneNumDTO dto) throws Exception {
        wxService.bingingPhone(dto);
        return Response.success("绑定成功");
    }

}
