package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.demo.config.KeepAliveService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		
		KeepAliveService keepAliveService = context.getBean(KeepAliveService.class);
		keepAliveService.startKeepAlive();
		
		System.out.println("Social Backend Application started successfully!");
		System.out.println("Keep-alive service is active to prevent server sleep.");
	}

}
