package com.example.fucc.constants;

/**
 * @program: fucc
 * @description: 查询ESB的结果码
 * @author: liuxiongfeng
 * @create: 2018-10-25 19:44
 **/
public class ConstantsUtils {
    public static final String ESB_SUCCESS_DATA = "10001";// 查询成功，有数据返回
    public static final String ESB_SUCCESS_NONE = "10002";// 查询成功，无数据返回
    public static final String ESB_FAILURE_DATA = "10003";// 查询数据库异常
    public static final String ESB_FAILURE_EXCEPTION = "10004";// ESB调用异常
    public static final String ESB_CON_EXCEPTION = "10005";// ESB链接异常
}
