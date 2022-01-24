package com.SmartHealthRemoteSystem.SHSR.SendNotification;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorData;
import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientRepository;
import com.SmartHealthRemoteSystem.SHSR.WebConfiguration.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.ExecutionException;
@Controller
@RequestMapping("/notification")
public class SendNotificationController {
    public final SensorDataRepository sensorDataRepository;
    public final PatientRepository patientRepository;
    public final DoctorRepository doctorRepository;
    public final SendNotificationService sendNotificationService;

    public SendNotificationController(SensorDataRepository sensorDataRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, SendNotificationService sendNotificationService) {
        this.sensorDataRepository = sensorDataRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.sendNotificationService = sendNotificationService;
    }
    @GetMapping()
    public String SendNotification(Model model, @RequestParam (value = "patientId")String patientID) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails= (MyUserDetails) auth.getPrincipal();
        Doctor doctor=doctorRepository.get(myUserDetails.getUsername());
        Patient patient=patientRepository.get(patientID);
        SensorData sensorData=sensorDataRepository.get(patient.getSensorDataId());
        String category=sendNotificationService.CheckAbnormalReadingECG(sensorData.getEcgReading());
        boolean temp=sendNotificationService.CheckBodyTemp(sensorData.getSensorDataId());
        String Oxygen=sendNotificationService.CheckOxygen(sensorData.getSensorDataId());
        String message=sendNotificationService.CheckCondition(sensorData.getSensorDataId(),category,temp,Oxygen);
        patient.setMessage(message);
        patientRepository.update(patient);
        return"doctor/doctorDashBoard";
    }
}
