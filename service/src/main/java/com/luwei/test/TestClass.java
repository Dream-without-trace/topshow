package com.luwei.test;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * <p> 测试模块
 *
 * @author Leone
 **/
public class TestClass {

    public static void main(String[] args) throws IOException {

        System.out.println(Thread.currentThread().getContextClassLoader().getResource(".").getPath().substring(1));
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File(TestClass.class.getResource("/").getPath().substring(1) + "/area.json");


    }

}
