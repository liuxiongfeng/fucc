package com.example.fucc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.fucc.dao")
@ComponentScan({"com.example.fucc.controller","com.example.fucc.service"})
public class FuccApplication {

    public static void main(String[] args) {
        SpringApplication.run(FuccApplication.class, args);
    }
}
