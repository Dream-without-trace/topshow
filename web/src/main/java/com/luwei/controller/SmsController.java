package com.luwei.controller;

import com.aliyuncs.exceptions.ClientException;
import com.luwei.common.exception.ExceptionMessage;
import com.luwei.common.exception.ValidateException;
import com.luwei.common.utils.AppUtils;
import com.luwei.module.alisms.AliSmsProperties;
import com.luwei.module.alisms.AliSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Leone
 * @since 2018-08-22
 **/
@Slf4j
@RestController
@Api(tags = "短信模块")
@RequestMapping("/api/sms")
public class SmsController {

    @Resource
    private AliSmsService aliSmsService;

    @Resource
    private AliSmsProperties aliSmsProperties;

    @ApiOperation("发送验证码")
    @GetMapping("/send")
    public void sendCode(@RequestParam String phone) throws ClientException {
        System.out.println("1");
        if (!AppUtils.isMobile(phone))
            throw new ValidateException(ExceptionMessage.PHONE_LAYOUT_FAIL);
        else {
            aliSmsService.send(phone);
        }
    }

}
