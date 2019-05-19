package com.db.messageFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages="com.db.*")
@ImportResource({"classpath*:applicationContext.xml"})
public class ApplicationMain{

	
	
	public static void main(String[] args) {

		SpringApplication.run(ApplicationMain.class, args);
		 
	}
	
		

}
