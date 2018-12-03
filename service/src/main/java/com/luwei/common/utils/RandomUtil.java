package com.luwei.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

/**
 * @author Leone
 * @since 2018-07-30
 **/
public class RandomUtil {

    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */
    public static String getNum(Integer length) {
        if (length < 1 || length > 512) {
            length = 32;
        }
        StringBuffer result = new StringBuffer();
        final String sources = "0123456789";
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            result.append(sources.charAt(rand.nextInt(9)) + "");
        }
        return result.toString();
    }

    /**
     * 生成32位随机数字
     *
     * @param length
     * @return
     */
    public static String getStr(Integer length) {
        if (length <= 0 || length > 32) {
            length = 32;
        }
        return UUID.randomUUID().toString().replace("-", "").substring(0, length);
    }

    /**
     * 获取当前时间戳，单位秒(10位)
     *
     * @return
     */
    public static String timestamp() {
        return System.currentTimeMillis() / 1000 + "";
    }

    /**
     * 获取当前时间戳，单位秒(14位)
     *
     * @return
     */
    public static String currentTimestamp() {
        return String.valueOf(System.currentTimeMillis());
    }


    /**
     * 获取带格式的日期字符串
     *
     * @param length
     * @return
     */
    public static String getDateStr(Integer length) {
        LocalDateTime localDateTime = LocalDateTime.now();
        String result = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        if (length < 1 || length > 17) {
            return result;
        } else {
            return result.substring(0, length);
        }
    }

    public static void main(String[] args) {
        System.out.println(getStr(34));
        System.out.println(getNum(35));
        System.out.println(timestamp());
        System.out.println(currentTimestamp());
        System.out.println(getDateStr(14));
    }


}
