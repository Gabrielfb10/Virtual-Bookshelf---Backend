package com.acirio.virtual_bookshelf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VirtualBookshelfApplication {

	public static void main(String[] args) {
		SpringApplication.run(VirtualBookshelfApplication.class, args);
	}

}
