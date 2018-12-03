package com.luwei.controllers;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.luwei.common.constants.RedisKeyPrefix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Leone
 * @since 2018-07-31
 **/
@RestController
@Api(tags = "验证码模块")
@RequestMapping("api/captcha")
public class CaptchaController {

    private static Logger logger = LoggerFactory.getLogger(CaptchaController.class);

    @Resource(name = "stringRedisTemplate")
    protected ValueOperations<String, String> operations;

    @Resource
    protected DefaultKaptcha captchaProducer;

    @GetMapping
    @ApiOperation("验证码")
    public void getCaptcha(@RequestParam @ApiParam("uuid") String uuid, HttpServletResponse res) {
        String key = RedisKeyPrefix.captcha(uuid);
        logger.info(" login uuid is {}", uuid);
        String capText = operations.get(key);
        if (StringUtils.isEmpty(capText)) {
            //生成验证码
            capText = captchaProducer.createText();
            logger.info("----------capText:" + capText + "----------------");
            //放入redis中，寿命为5分钟
            operations.set(RedisKeyPrefix.captcha(uuid), capText, 5, TimeUnit.MINUTES);
        }

        res.setDateHeader("Expires", 0);
        res.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        res.setHeader("Cache-Control", "post-check=0, pre-check=0");
        res.setHeader("Pragma", "no-cache");
        res.setContentType("image/jpeg");
        try (ServletOutputStream out = res.getOutputStream()) {
            BufferedImage bufferedImage = captchaProducer.createImage(capText);
            ImageIO.write(bufferedImage, "jpg", out);
            out.flush();
        } catch (IOException e) {
            logger.info("----------验证码输出错误:{}--------", e.getMessage());
        }
    }

}