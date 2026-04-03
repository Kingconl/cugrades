package com.connor.cu_grades;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarletonGradesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarletonGradesApplication.class, args);
	}

}
