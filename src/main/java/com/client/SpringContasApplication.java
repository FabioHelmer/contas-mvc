package com.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@SpringBootApplication
public class SpringContasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringContasApplication.class, args);
	}

}
