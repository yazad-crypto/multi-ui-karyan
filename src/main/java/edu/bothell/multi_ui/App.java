package edu.bothell.multi_ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "edu.bothell.multi_ui")
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
