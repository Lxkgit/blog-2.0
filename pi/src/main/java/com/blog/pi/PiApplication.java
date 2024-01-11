package com.blog.pi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.blog.pi.dao")
public class PiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PiApplication.class, args);
	}

}
