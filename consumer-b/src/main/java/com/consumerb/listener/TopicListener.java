package com.consumerb.listener;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class TopicListener {

    @JmsListener(destination = "publish.topic", containerFactory = "jmsListenerContainerTopic")
    public void receive(String text){
        System.out.println("TopicListener: consumer-b 收到一条信息: " + text);
    }
}