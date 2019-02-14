package com.example.demo.controller;

import com.example.demo.service.stuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by wangjiang on 2019/1/29.
 */
@Controller
@RequestMapping("/stu")
public class stuController
{
    @Resource
    private stuService stuservice;

    @RequestMapping("/showStus")
    public String showStus(ModelMap map){
        map.addAttribute("stuLists",stuservice.findList());
        return "stuLIst";
    }
}
