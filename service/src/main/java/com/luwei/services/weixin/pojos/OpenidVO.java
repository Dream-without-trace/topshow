package com.luwei.services.weixin.pojos;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * @author Leone
 * @since 2018-08-02
 **/
@ApiModel
public class OpenidVO implements Serializable {

    private String openid;

    private String session_key;

    private String token;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "OpenidVO{" +
                "openid='" + openid + '\'' +
                ", session_key='" + session_key + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
