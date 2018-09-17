package com.example.fucc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan({"com.example.fucc.controller","com.example.fucc.service","com.example.fucc.interceptor"})
@ServletComponentScan
public class FuccApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuccApplication.class, args);
    }
}
