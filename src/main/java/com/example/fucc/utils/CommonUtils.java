package com.example.fucc.utils;

import com.alibaba.fastjson.JSON;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @program: fucc
 * @description: 通用类
 * @author: liuxiongfeng
 * @create: 2018-10-31 20:11
 **/
public class CommonUtils {
    public static void printInfo(HttpServletResponse httpServletResponse,String msg) throws IOException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        OutputStream out = httpServletResponse.getOutputStream();
        String str = JSON.toJSONString(msg);
        out.write(str.getBytes("UTF-8"));
        out.flush();
        out.close();
    }

}
