package com.example.demo.web;

import com.alibaba.dubbo.config.annotation.Reference;
import com.interfaces.PublishService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by wangjiang on 2019/1/29.
 */
@RestController
public class HelloController {

    // 使用兼容注入，可以使用dubbo原生注解@Reference注入
    @Reference(version = "1.0.0")
    private PublishService service;

    @RequestMapping("/hello/{msg}")
    public void index(@PathVariable String msg) {
        if(msg == null || "".equals(msg)){
            msg = "hello World";
        }
        service.sendMessage(msg);
    }
}
