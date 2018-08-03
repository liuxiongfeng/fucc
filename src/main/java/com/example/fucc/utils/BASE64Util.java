package com.example.fucc.utils;/*
 * @Author liuxiongfeng
 * @Description
 */

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class BASE64Util {

    // BASE64编码
    public static String encode(String s) {
        BASE64Encoder encoder = new BASE64Encoder();
        try {
            s = encoder.encode(s.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        return s;
    }

    // BASE64解码
    public static String decode(String s) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] bytes = decoder.decodeBuffer(s);
            s = new String(bytes, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }

    //测试一下
    public static void main(String[] args) {
        String a = encode("qwer");
        String b = decode(a);
        System.out.println(b);
    }
}
