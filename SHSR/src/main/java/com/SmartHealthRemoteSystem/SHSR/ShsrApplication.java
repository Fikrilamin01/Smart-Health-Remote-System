package com.SmartHealthRemoteSystem.SHSR;

import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusRepository;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.Prescription;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.PrescriptionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;


@SpringBootApplication
public class ShsrApplication implements CommandLineRunner{

	private final PrescriptionService prescriptionService;

	public ShsrApplication( PrescriptionService prescriptionService) {
		this.prescriptionService = prescriptionService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ShsrApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
