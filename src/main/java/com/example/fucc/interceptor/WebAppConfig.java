package com.example.fucc.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: fucc
 * @description: 注册拦截器
 * @author: liuxiongfeng
 * @create: 2018-09-14 11:17
 **/
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册自定义拦截器，添加拦截路径和排除拦截路径
       // registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/**").excludePathPatterns("/server");
        //拦截所有的请求
        registry.addInterceptor(new InterceptorConfig()).addPathPatterns("/app/**");
    }
}
