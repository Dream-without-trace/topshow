package com.luwei.services.weixin.pojos;

import io.swagger.annotations.ApiModel;

/**
 * <p>
 *
 * @author Leone
 * @since 2018-08-30
 **/
@ApiModel
public class PhoneEncrypted {

    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;

    private PhoneEncryptedInfo watermark;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public PhoneEncryptedInfo getWatermark() {
        return watermark;
    }

    public void setWatermark(PhoneEncryptedInfo watermark) {
        this.watermark = watermark;
    }

    @Override
    public String toString() {
        return "PhoneEncrypted{" +
                "phoneNumber='" + phoneNumber + '\'' +
                ", purePhoneNumber='" + purePhoneNumber + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", watermark=" + watermark +
                '}';
    }

    private static class PhoneEncryptedInfo {

        private String appid;

        private String timestamp;

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }

        @Override
        public String toString() {
            return "PhoneEncryptedInfo{" +
                    "appid='" + appid + '\'' +
                    ", timestamp='" + timestamp + '\'' +
                    '}';
        }
    }

}

