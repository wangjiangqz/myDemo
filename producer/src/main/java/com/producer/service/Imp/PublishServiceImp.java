package com.producer.service.Imp;

import com.alibaba.dubbo.config.annotation.Service;
import com.interfaces.PublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import javax.jms.Queue;

/**
 * Created by wangjiang on 2019/2/13.
 */
@Service(version = "1.0.0")
public class PublishServiceImp implements PublishService
{
    @Autowired
    private JmsMessagingTemplate jms;

    @Autowired
    private Queue queue;

    @Override
    public  void sendMessage(String msg){
        jms.convertAndSend(queue, "++++++++"+msg+"++++++++");
    }
}
