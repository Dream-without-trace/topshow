package com.luwei.module.qiniu;

/**
 * <p>
 *
 * @author Leone
 **/
public class QiNiuToken {

    private String token;

    private String linkAddress;

    public QiNiuToken() {
    }

    public QiNiuToken(String token, String linkAddress) {
        this.token = token;
        this.linkAddress = linkAddress;
    }

    public String getLinkAddress() {
        return linkAddress;
    }

    public void setLinkAddress(String linkAddress) {
        this.linkAddress = linkAddress;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
