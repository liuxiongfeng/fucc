package com.example.fucc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by jt on 1/10/17.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public ModelAndView index(Model model) {
       // ModelAndView modelAndView = new ModelAndView("font/dialogApp");
        ModelAndView modelAndView = null;
        try {
             modelAndView = new ModelAndView("info");
           // modelAndView.addObject("one","onevalue");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return modelAndView;
    }

    @RequestMapping("/jsp1")
    public String getjsp1(Model model) {
        return "hhh";
    }

    @RequestMapping("/html")
    public String getAppServerInfo(String request) {
        return "dialogApp";
    }
    @RequestMapping("/html1")
    public String getAppServerInfo1(String request) {
        return "dialogApp";
    }
}
