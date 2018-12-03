package com.luwei.common.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author Leone
 * @since 2018-07-30
 **/
public class BcryptUtil {

    private BcryptUtil() {
    }

    public static String encode(String code) {
        return BCrypt.hashpw(code, BCrypt.gensalt());
    }

    public static void main(String[] args) {
        System.out.println(encode("manager"));
//        System.out.println(matching("chen", "$2a$10$KBqTJJftce1ej8ZVBMBBEec7S77g5b0Ip9LXZWIVlRXaAFxni13Gq"));
        System.out.println(matching("root3", "$2a$10$SbL4WG0Ox5YmS9neOj/Kg.Lr3LxgS6oev9wVZycpt0ZJaydldLpxe"));
    }

    public static boolean matching(String password, String hashed) {
        try {
            return BCrypt.checkpw(password, hashed);
        } catch (Exception e) {
            return false;
        }
    }
}
