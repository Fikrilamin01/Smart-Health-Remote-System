package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
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
    private final SensorDataService sensorDataService;
    public SendHealthStatusController(HealthStatusService healthStatusService, PatientService patientService, SensorDataService sensorDataService) {
        this.healthStatusService = healthStatusService;
        this.patientService=patientService;
        this.sensorDataService=sensorDataService;
    }

    @PostMapping("/create-healthstatus/{patientId}")
    public void saveHealthStatus(@RequestBody HealthStatus healthStatus, @PathVariable String patientId)
            throws ExecutionException, InterruptedException {
        healthStatusService.createHealthStatus(healthStatus, patientId);
    }

    @GetMapping("/get-healthstatus/{patientId}/{healthStatusId}")
    public HealthStatus getHealthStatus(@PathVariable String healthStatusId, @PathVariable String patientId) throws ExecutionException, InterruptedException {

        return healthStatusService.getHealthStatus(healthStatusId, patientId);
    }

    @PutMapping("/update-healthstatus/{patientId}")
    public void updateHealthStatus(@RequestBody HealthStatus healthStatus, @PathVariable String patientId) throws ExecutionException, InterruptedException {
        healthStatusService.updateHealthStatus(healthStatus, patientId);
    }

    @DeleteMapping("/delete-healthstatus/{patientId}/{healthStatusId}")
    public void deleteHealthStatus(@PathVariable String healthStatusId, @PathVariable String patientId) throws ExecutionException, InterruptedException {
        healthStatusService.deleteHealthStatus(healthStatusId, patientId);
    }

    @PostMapping("/sendHealthStatus")
    public String sendHealthStatus(@RequestParam(value = "symptom") String symptom, @RequestParam(value="patientId") String patientId, @RequestParam (value = "doctorId")String doctorId, Model model) throws ExecutionException, InterruptedException {


        String sensorId=patientService.getPatientSensorId(patientId);
        symptom+="\n"+ sensorDataService.stringSensorData(sensorId);
        HealthStatus healthStatus=new HealthStatus(symptom,doctorId);
        healthStatusService.createHealthStatus(healthStatus,patientId);

        Patient patient=patientService.getPatient(patientId);
        Doctor doctor=patientService.findDoctorThroughHealthStatusPatient(patient);
        model.addAttribute(patient);
        model.addAttribute(doctor);
        return "patientDashBoard";
    }

    @PostMapping("/viewHealthStatusForm")
    public String healthStatusForm(@RequestParam (value = "patientId") String patientId, Model model) throws ExecutionException, InterruptedException {
//        List<HealthStatus>  healthStatus=healthStatusService.getListHealthStatus(patientID);
//        HealthStatus firstHealthStatus=healthStatus.get(0);

        Patient patient=patientService.getPatient(patientId);
        Doctor doctor=patientService.findDoctorThroughHealthStatusPatient(patient);
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);

        return "sendDailyHealthSymptom";

    }

}




