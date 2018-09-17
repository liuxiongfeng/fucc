package com.example.fucc.interceptor;

import com.example.fucc.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @program: fucc
 * @description: 进入controller层之前拦截请求
 * @author: liuxiongfeng
 * @create: 2018-09-14 11:19
 **/
public class InterceptorConfig implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);
    @Autowired
    AppService appService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        logger.info("---------------------开始进入请求地址拦截----------------------------");
        String token = httpServletRequest.getHeader("token");
        String userId = httpServletRequest.getHeader("userId");
        if (null != token && null != userId){
            //验证token
            boolean b1 = appService.loginCookieQuery(userId, token);
            if (b1) {
                return true;
            } else {
                PrintWriter printWriter = httpServletResponse.getWriter();
                printWriter.write("{code:0,message:\"token is invalid,please login again!\"}");
                logger.error("token未通过验证!");

                //先不拦截
                return true;
            }
        }else {
            return true;
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
