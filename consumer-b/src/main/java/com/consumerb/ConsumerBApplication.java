package com.consumerb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ConsumerBApplication
{

	public static void main(String[] args) {
		SpringApplication.run(ConsumerBApplication.class, args);
	}

}

