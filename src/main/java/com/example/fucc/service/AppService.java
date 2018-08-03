package com.example.fucc.service;/*
 * @Author liuxiongfeng
 * @Description
 */

public interface AppService {
    public boolean VerifySignKey(String bizcode,String data,String sign,String timestamp);

    public boolean loginCookieQuery(String userId, String token);

}
