package com.greglturnquist.hackingspringboot.reactive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.blockhound.BlockHound;

@SpringBootApplication
public class HackingSpringBootApplication {

	public static void main(String[] args) {

		SpringApplication.run(HackingSpringBootApplication.class, args);
	}

}
