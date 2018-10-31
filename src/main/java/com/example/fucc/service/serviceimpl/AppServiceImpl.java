package com.example.fucc.service.serviceimpl;/*
 * @Author liuxiongfeng
 * @Description
 */

import com.example.fucc.config.FunctionProperties;
import com.example.fucc.service.AppService;
import com.example.fucc.utils.EsbUtils;
import com.example.fucc.utils.MD5Util;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService {


    //验证请求是否符合参数+signkey的加密过程
    @Override
    public boolean verifySignKey(String bizcode, String data, String sign, String timestamp) {
        String signkey = FunctionProperties.getString("signkey");
        //客户端md5之前的字符串
        String str = "bizcode=" + bizcode + "&data=" + data + "&signkey=" + signkey + "&timestamp" + timestamp;
        try {
            String md5str = MD5Util.md5(str);
            if (md5str.equals(sign)){
                return true;
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean loginCookieQuery(String userid, String token) {
        try {
            boolean b = EsbUtils.loginCookieQuery(userid, token);
            return b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
