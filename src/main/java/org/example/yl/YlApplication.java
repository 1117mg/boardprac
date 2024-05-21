package org.example.yl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@Component
@SpringBootApplication
@MapperScan(basePackages = "org.example.yl")
public class YlApplication {

	public static void main(String[] args) {
		SpringApplication.run(YlApplication.class, args);
	}

}
