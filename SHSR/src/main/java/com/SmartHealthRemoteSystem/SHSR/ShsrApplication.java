package com.SmartHealthRemoteSystem.SHSR;

import com.SmartHealthRemoteSystem.SHSR.User.User;
import com.SmartHealthRemoteSystem.SHSR.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ShsrApplication {


	public static void main(String[] args) {
		SpringApplication.run(ShsrApplication.class, args);
	}

}
