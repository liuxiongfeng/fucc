package com.example.fucc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.fucc.utils.HttpClientResult;
import com.example.fucc.utils.HttpClientUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;


@RestController
public class ToXiaoiController {

    @RequestMapping("/index")
    public String index() {
        ModelAndView modelAndView = new ModelAndView("font/dialogApp");
        return "font/dialogApp";
    }


    @RequestMapping("/question")
    public String question(String question){
        String content = null;
        HttpClientResult result = null;
        try {
            Map<String,String> map = new HashMap<>();
            map.put("userId","123");
            map.put("platform","app");
            map.put("question",question);
            result = HttpClientUtil.doPost("http://192.168.25.170:9921/robot-swhy/http/RobotService",map);
            JSONObject jsonObject = JSON.parseObject(result.getContent());
            if (jsonObject.containsKey("content")){
                content = jsonObject.get("content").toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return content;
    }

    /*
     * @Author: liuxiongfeng
     * @Date: 10:39 2018-7-20
     * @Description:响应前端请求,向小i平台获取数据
     **/
    @RequestMapping("/getAnswer")
    public JSONObject questionby(HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObject = null;
        HttpClientResult result = null;
        try {
            Map<String,String> map = new HashMap<>();
            map.put("userId",request.getParameter("userId"));
            map.put("platform",request.getParameter("platform"));
            map.put("question",request.getParameter("question"));
            result = HttpClientUtil.doPost("http://192.168.25.170:9921/robot-swhy/http/RobotService",map);
            jsonObject = JSON.parseObject(result.getContent());
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
}
