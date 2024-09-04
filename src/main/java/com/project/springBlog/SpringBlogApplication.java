package com.project.springBlog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.cdimascio.dotenv.Dotenv;

import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class SpringBlogApplication {

	public static void main(String[] args) {
		if(Files.exists(Paths.get(".env"))){
			Dotenv dotenv = Dotenv.load();
			dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));
		}
		SpringApplication.run(SpringBlogApplication.class, args);
	}
}
