package com.SmartHealthRemoteSystem.SHSR;

import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatus;
import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ShsrApplication implements CommandLineRunner{

	private final HealthStatusRepository healthStatusRepository;

	public ShsrApplication(HealthStatusRepository healthStatusRepository) {
		this.healthStatusRepository = healthStatusRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ShsrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
