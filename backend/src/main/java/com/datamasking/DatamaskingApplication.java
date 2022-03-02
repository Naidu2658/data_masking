package com.datamasking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.datamasking.controllers"})
public class DatamaskingApplication {

	public static void main(String[] args) {

		SpringApplication.run(DatamaskingApplication.class, args);
	}

}
