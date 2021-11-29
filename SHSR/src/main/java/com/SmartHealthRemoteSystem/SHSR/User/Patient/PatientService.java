package com.SmartHealthRemoteSystem.SHSR.User.Patient;


import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusService;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PatientService {


    private final PatientRepository patientRepository;
    private final HealthStatusService healthStatusService;
    private final PrescriptionService prescriptionService;

    @Autowired
    public PatientService(PatientRepository patientRepository, HealthStatusService healthStatusService, PrescriptionService prescriptionService) {
        this.patientRepository = patientRepository;
        this.healthStatusService = healthStatusService;
        this.prescriptionService = prescriptionService;
    }

    public void createPatient(Patient patient) throws ExecutionException, InterruptedException {
        String timeCreated = patientRepository.createPatient(patient);
    }

    public void deletePatient(String patientId) throws ExecutionException, InterruptedException {
        //Search in the database whether the patient exist or not
        if(patientRepository.getPatient(patientId) == null){
            //show error message
            String message = "patientId is not exist in the database";
        }else{
            String message = patientRepository.deletePatient(patientId);
            //Show success message
        }

        //Delete all health status patient in the database
        healthStatusService.deleteAllHealthStatus(patientId);

        //Delete all prescription patient in the database
        prescriptionService.deleteAllPrescription(patientId);
    }

    public void updatePatient(Patient patient) throws ExecutionException, InterruptedException {
        patientRepository.updatePatient(patient);
    }

    public Patient getPatient(String patientId) throws ExecutionException, InterruptedException {
        Patient patient = patientRepository.getPatient(patientId);
        if(patient != null){
            return patient;
        }else{
            return null;
        }
    }

    public boolean patientAuthentication(String userId, String password) throws ExecutionException, InterruptedException {
        Patient patient = getPatient(userId);
        if(patient == null){
            return false;
        }else{
            if(patient.getPassword().equals(password)){
                return true;
            }else{
                return false;
            }
        }
    }
}
