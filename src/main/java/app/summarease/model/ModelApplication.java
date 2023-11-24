package app.summarease.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ModelApplication {

	@GetMapping("/message")
	public String getMessage() {
		return "Welcome to Summarease";
	}

	public static void main(String[] args) {
		SpringApplication.run(ModelApplication.class, args);
	}

}