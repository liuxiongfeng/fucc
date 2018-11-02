package com.example.fucc.interceptor;

import com.alibaba.fastjson.JSON;
import com.example.fucc.service.AppService;
import com.example.fucc.utils.CommonUtils;
import com.example.fucc.utils.EsbUtils;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.OutputStream;
import java.io.PrintWriter;

import static com.sun.xml.internal.ws.api.message.Packet.State.ServerResponse;

/**
 * @program: fucc
 * @description: 进入controller层之前拦截请求
 * @author: liuxiongfeng
 * @create: 2018-09-14 11:19
 **/
public class InterceptorConfig implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);
    @Autowired
    private AppService appService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.info("---------------------开始进入请求地址拦截----------------------------");

        if (httpServletRequest.getQueryString() != null && !httpServletRequest.getQueryString().contains("token")){
            logger.error("token不存在!");
            /*httpServletResponse.setContentType("application/json;charset=UTF-8");
            OutputStream out = httpServletResponse.getOutputStream();
            String str = JSON.toJSONString("token不存在");
            out.write(str.getBytes("UTF-8"));
            out.flush();
            out.close();*/
            CommonUtils.printInfo(httpServletResponse,"token不存在");
            return false;
        }
        String token = httpServletRequest.getHeader("token");
        if (StringUtils.isEmpty(token)){
            token = httpServletRequest.getParameter("token");
        }
        if (null != token){
            //验证token
            boolean b1 = EsbUtils.loginCookieQuery(null, token);
            if (b1) {
                return true;
            } else {
               /* PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.write("{code:0,message:\"token is invalid,please login again!\"}");*/
                logger.error("token未通过验证!");
               /* httpServletResponse.setContentType("application/json;charset=UTF-8");
                OutputStream out = httpServletResponse.getOutputStream();
                String str = JSON.toJSONString("token未通过验证!");
                out.write(str.getBytes("UTF-8"));
                out.flush();
                out.close();*/
                CommonUtils.printInfo(httpServletResponse,"token未通过验证");
                return false;
            }
        }else {
            logger.error("token为空!");
           /*httpServletResponse.setContentType("application/json;charset=UTF-8");
            OutputStream out = httpServletResponse.getOutputStream();
            String str = JSON.toJSONString("token为空!");
            out.write(str.getBytes("UTF-8"));
            out.flush();
            out.close();*/
            CommonUtils.printInfo(httpServletResponse,"token为空");
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
