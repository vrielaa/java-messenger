package com.messenger.javamessenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class JavaMessengerApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaMessengerApplication.class, args);
	}
}
