package com.luwei.services.weixin.pojos;

import io.swagger.annotations.ApiModel;
import lombok.ToString;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-30
 **/
@ToString
@ApiModel
public class PhoneNumDTO {

    private String errMsg;

    private String iv;

    private String encryptedData;

    private Integer userId;

    private String sessionKey;

    private String referrerPhone;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getReferrerPhone() {
        return referrerPhone;
    }

    public void setReferrerPhone(String referrerPhone) {
        this.referrerPhone = referrerPhone;
    }
}
