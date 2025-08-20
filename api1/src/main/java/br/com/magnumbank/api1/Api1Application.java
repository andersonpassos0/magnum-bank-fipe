package br.com.magnumbank.api1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Api1Application {

	public static void main(String[] args) {
		SpringApplication.run(Api1Application.class, args);
		System.out.println("API 1 rodando...");
	}

}
