package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorService;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/Health-status")
public class SendHealthStatusController {
    private final HealthStatusService healthStatusService;
    private final PatientService patientService;
     final SensorDataService sensorDataService;
    private final DoctorService doctorService;
    public SendHealthStatusController(HealthStatusService healthStatusService, PatientService patientService, SensorDataService sensorDataService, DoctorService doctorService) {
        this.healthStatusService = healthStatusService;
        this.patientService=patientService;
        this.sensorDataService=sensorDataService;
        this.doctorService = doctorService;
    }

    @PostMapping("/sendHealthStatus")
    public String sendHealthStatus(@RequestParam(value = "symptom") String symptom,
                                   @RequestParam(value="patientId") String patientId,
                                   @RequestParam (value = "doctorId")String doctorId,
                                   Model model) throws ExecutionException, InterruptedException {


        HealthStatus healthStatus=new HealthStatus(symptom,doctorId);
        healthStatusService.createHealthStatus(healthStatus,patientId);

        Patient patient=patientService.getPatient(patientId);
        Doctor doctor=doctorService.getDoctor(patient.getAssigned_doctor());
        model.addAttribute(patient);
        model.addAttribute(doctor);
        return "patientDashBoard";
    }

    @PostMapping("/viewHealthStatusForm")
    public String healthStatusForm(@RequestParam (value = "patientId") String patientId, Model model) throws ExecutionException, InterruptedException {

        Patient patient=patientService.getPatient(patientId);
        Doctor doctor=doctorService.getDoctor(patient.getAssigned_doctor());
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);

        return "sendDailyHealthSymptom";
    }
}




