package com.SmartHealthRemoteSystem.SHSR.ViewPatientHealthStatus;

import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatus;
import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusService;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorService;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/viewPatientHealthStatus")
public class ViewPatientHealthStatusController {
    private final HealthStatusService healthStatusService;
    private final PatientService patientService;
    private final DoctorService doctorService;


    @Autowired
    public ViewPatientHealthStatusController(HealthStatusService healthStatusService, DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
        this.healthStatusService = healthStatusService;
    }

    @PostMapping("/a")
    public String getHealthStatus(@RequestParam("patientId") String patientId, @RequestParam("doctorId") String doctorId, Model model) throws ExecutionException, InterruptedException {
        //Retrieve information
        Patient patient = patientService.getPatient(patientId);
        Doctor doctor = doctorService.getDoctor(doctorId);

        //Retrive patient list of health status
        List<HealthStatus> healthStatus = healthStatusService.getListHealthStatus(patientId);
        List<HealthStatus> threeLastHealth = null;
        for (int i = healthStatus.size() - 1; i >= (healthStatus.size() - 3); i--) {
            threeLastHealth.add(healthStatus.get(i));
        }
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("healthStatusList", healthStatus);

        return "viewPatientHealthStatus";
    }
}
