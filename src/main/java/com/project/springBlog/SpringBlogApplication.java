package com.project.springBlog;

import io.github.cdimascio.dotenv.Dotenv;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBlogApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().directory("./").load();

		// Configura y carga las variables de entorno
		dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication.run(SpringBlogApplication.class, args);
	}
}
