package com.example.fucc.utils;/*
 * @Author liuxiongfeng
 * @Description
 */

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    /**
     * MD5方法 不加盐
     *
     * @param text 明文
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text) throws Exception {
        //加密后的字符串
        String encodeStr=DigestUtils.md5Hex(text);
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5方法
     *
     * @param text 明文
     * @param key 密钥
     * @return 密文
     * @throws Exception
     */
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr=DigestUtils.md5Hex(text + key);
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     *
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            System.out.println("MD5验证通过");
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        String data = "{'userid':'admin','iid':'1'}";
        String data64 = BASE64Util.encode(data);
        String bizcode = "function1";
        String timestamp = "121212112";
        String signkey = "liuxiongfeng";

        String all = "bizcode=" + bizcode + "&data=" + data64 + "&signkey=" + signkey + "&timestamp" + timestamp;
        try {
            String sign = md5(all);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
