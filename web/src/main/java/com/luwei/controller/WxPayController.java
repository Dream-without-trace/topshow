package com.luwei.controller;

import com.luwei.common.utils.RandomUtil;
import com.luwei.services.pay.PayService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Leone
 * @since Monday August 1
 **/
@Slf4j
@RestController
@Api(tags = "支付模块")
@RequestMapping("/api/pay")
public class WxPayController {

    @Resource
    private PayService payService;

    @RequestMapping(value = "/notify", method = {RequestMethod.POST, RequestMethod.GET})
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        payService.wxNotify(request, response);
    }

    @RequestMapping(value = "/pay", method = {RequestMethod.POST, RequestMethod.GET})
    public Map<String, String> wxPay(HttpServletRequest request, @RequestParam String openid) throws Exception {
        String outTradeNo = "T" + 1 + RandomUtil.getNum(14);
        return payService.xcxPay(1, openid, outTradeNo, request);
    }

}
