package com.luwei.controllers;

import com.luwei.services.manager.LoginService;
import com.luwei.services.manager.cms.LoginSuccessVO;
import com.luwei.services.manager.cms.ManagerLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@RestController
@Api(tags = "登录模块")
@RequestMapping("/api")
public class LoginController {

    @Resource
    private LoginService loginService;

    @PostMapping("/login")
    @ApiOperation("登录")
    public LoginSuccessVO login(@RequestBody @Valid ManagerLoginVO loginVO) {
        return loginService.login(loginVO);
    }


    @PostMapping("/logout")
    @ApiOperation("退出")
    public void logout(@RequestParam Integer userId) {
        loginService.logout(userId);
    }


}
