package org.isabella.revdol;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("org.isabella.revdol.persistence")
@ComponentScan()
public class RevdolApplication {

	public static void main(String[] args) {
		SpringApplication.run(RevdolApplication.class, args);
	}

}
