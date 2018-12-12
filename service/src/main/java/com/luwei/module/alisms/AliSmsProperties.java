package com.luwei.module.alisms;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ConfigurationProperties(prefix = "luwei.module.sms")
public class AliSmsProperties {

    private String signName;

    private String templateCode;

    /**
     * 报名课程
     */
    private String enrollCoursesCode;
    /**
     * 报名活动
     */
    private String enrollActivityCode;
    /**
     * 注册会员
     */
    private String registerMemberCode;

    private String templateCodeName = "code";

    private Long expireTime = 5L;

    private Integer captchaLength = 4;

    private String accessKeyId;

    private String accessKeySecret;

    private String redisPrefix = "phone.captcha";

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTemplateCodeName() {
        return templateCodeName;
    }

    public void setTemplateCodeName(String templateCodeName) {
        this.templateCodeName = templateCodeName;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public Integer getCaptchaLength() {
        return captchaLength;
    }

    public void setCaptchaLength(Integer captchaLength) {
        this.captchaLength = captchaLength;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getRedisPrefix() {
        return redisPrefix;
    }

    public void setRedisPrefix(String redisPrefix) {
        this.redisPrefix = redisPrefix;
    }

    public String getEnrollCoursesCode() {
        return enrollCoursesCode;
    }

    public void setEnrollCoursesCode(String enrollCoursesCode) {
        this.enrollCoursesCode = enrollCoursesCode;
    }

    public String getEnrollActivityCode() {
        return enrollActivityCode;
    }

    public void setEnrollActivityCode(String enrollActivityCode) {
        this.enrollActivityCode = enrollActivityCode;
    }

    public String getRegisterMemberCode() {
        return registerMemberCode;
    }

    public void setRegisterMemberCode(String registerMemberCode) {
        this.registerMemberCode = registerMemberCode;
    }
}
