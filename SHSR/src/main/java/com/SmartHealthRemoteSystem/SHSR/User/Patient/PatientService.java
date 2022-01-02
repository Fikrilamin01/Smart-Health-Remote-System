package com.SmartHealthRemoteSystem.SHSR.User.Patient;


import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatus;
import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusService;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorService;
import com.SmartHealthRemoteSystem.SHSR.User.User;
import com.SmartHealthRemoteSystem.SHSR.User.UserService;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.Prescription;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PatientService {

    private UserService userService;
    private PatientRepository patientRepository;
    private HealthStatusService healthStatusService;
    private PrescriptionService prescriptionService;
    private SensorDataService sensorDataService;
    private DoctorService doctorService;

    @Autowired
    public PatientService(UserService userService, PatientRepository patientRepository,
                          HealthStatusService healthStatusService, PrescriptionService prescriptionService,
                          SensorDataService sensorDataService, DoctorService doctorService) {
        this.userService = userService;
        this.patientRepository = patientRepository;
        this.healthStatusService = healthStatusService;
        this.prescriptionService = prescriptionService;
        this.sensorDataService = sensorDataService;
        this.doctorService = doctorService;
    }

    public String createPatient(Patient patient) throws ExecutionException, InterruptedException {
        //Create a temporary User
        User user = new User(patient.getUserId(), patient.getName(), patient.getPassword(), patient.getContact(), patient.getRole());
        Boolean result = userService.createUser(user);
        if(result == true){
            //there is no conflict with the ID
            //proceed to create the patient in patient table
            String timeCreated = patientRepository.savePatient(patient);
            return timeCreated;
        }
        return "Failed to create user patient with userId: " + patient.getUserId();
    }

    public void deletePatient(String patientId) throws ExecutionException, InterruptedException {
        //Search in the database whether the patient exist or not
        if(patientRepository.getPatient(patientId) == null){
            //show error message
            String message = "patientId is not exist in the database";
        }else{
            Patient patient = getPatient(patientId);
            String message = patientRepository.deletePatient(patientId);
            String timeDelete = userService.deleteUser(patientId);
            String timeDelete1 = sensorDataService.deleteSensorData(patient.getSensorDataId());
            //Show success message
        }

        //Delete all health status patient in the database
        healthStatusService.deleteAllHealthStatus(patientId);

        //Delete all prescription patient in the database
        prescriptionService.deleteAllPrescription(patientId);
    }

    public void updatePatient(Patient patient) throws ExecutionException, InterruptedException {
        User user = new User(patient.getUserId(), patient.getName(), patient.getPassword(), patient.getContact(), patient.getRole());
        String timeUpdate = userService.updateUser(user);
        String timeUpdate1 = patientRepository.updatePatient(patient);
    }

    public Patient getPatient(String patientId) throws ExecutionException, InterruptedException {
        Patient patient = patientRepository.getPatient(patientId);
        return patient;
    }

    public String getPatientSensorId(String patientId) throws ExecutionException, InterruptedException {
        Patient patient = patientRepository.getPatient(patientId);
        return patient.getSensorDataId();
    }

    public boolean patientAuthentication(String userId, String password) throws ExecutionException, InterruptedException {
        Patient patient = getPatient(userId);
        if(patient == null){
            return false;
        }else{
            return patient.getPassword().equals(password);
        }
    }

    public Prescription getPrescription(String patientId) throws ExecutionException, InterruptedException {
        Prescription prescription = null;

        List<Prescription> prescriptionList =  prescriptionService.getListPrescription(patientId);
        if(!prescriptionList.isEmpty()){
            prescription = prescriptionList.get(prescriptionList.size()-1);
        }

        return prescription;
    }

    public List<Patient> getPatientList(String patientId) throws ExecutionException, InterruptedException {
        return patientRepository.getListPatient();
    }

    public Doctor findDoctorThroughHealthStatusPatient(Patient patient) throws ExecutionException, InterruptedException {
        List<HealthStatus> healthStatusList = healthStatusService.getListHealthStatus(patient.getUserId());
        String doctorId = healthStatusList.get(0).getDoctorId();
        return doctorService.getDoctor(doctorId);
    }
}
