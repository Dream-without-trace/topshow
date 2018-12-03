package com.luwei.common.constants;

/**
 * @author Leone
 * @since 2018-07-30
 **/
public class RedisKeyPrefix {

    public static String captcha(String uuid) {
        return "captcha:" + uuid;
    }

    public static String index(int protoId) {
        return "index:" + protoId;
    }

}
