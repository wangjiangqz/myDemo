package com.example.demo.controller;

import com.example.demo.entity.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangjiang on 2019/1/30.
 */

@Controller
public class loginController
{
    @RequestMapping("/")
    public String index(ModelMap map) {
        Subject subject = SecurityUtils.getSubject();
        try
        {
            User user = (User) subject.getPrincipal();
            map.put("user",user);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/loginPost")
    @ResponseBody
    public Map loginUser(String username, String password, HttpSession session) {
        UsernamePasswordToken usernamePasswordToken=new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        Map map = new HashMap();
        try {
            subject.login(usernamePasswordToken);   //完成登录
            User user=(User) subject.getPrincipal();
            session.setAttribute("user", user);
            map.put("success",true);
            return map;
        } catch(Exception e) {
            map.put("success",false);
            map.put("message","登录失败！");
            return map;
        }
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
}
