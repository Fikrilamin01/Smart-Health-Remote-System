package com.SmartHealthRemoteSystem.SHSR;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataRepository;
import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ShsrApplication implements CommandLineRunner{





	public static void main(String[] args) {
		SpringApplication.run(ShsrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {




	}
}
