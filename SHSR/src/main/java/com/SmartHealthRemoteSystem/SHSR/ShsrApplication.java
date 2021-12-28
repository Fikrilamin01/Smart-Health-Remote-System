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

	private final HealthStatusRepository healthStatusRepository;
	private final SensorDataRepository sensordatarepository;
	private final PatientRepository patientRepository;

	public ShsrApplication(HealthStatusRepository healthStatusRepository, SensorDataRepository sensordatarepository, PatientRepository patientRepository) {
		this.healthStatusRepository = healthStatusRepository;
		this.sensordatarepository = sensordatarepository;
		this.patientRepository = patientRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ShsrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		patientRepository.savePatient(new Patient( "kesh_1",  "rakesh", "123",  "012345690",  "PATIENT",  "",  "1275",  "sheesh"));
		sensordatarepository.CreateTest("kesh_1");


	}
}
