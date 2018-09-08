package com.github.vspro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.github.vspro.persistent.dao")
public class BootOauth2DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootOauth2DemoApplication.class, args);
	}
}
