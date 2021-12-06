package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping("/create-patient")
    public String savePatient(@RequestBody Patient patient)
            throws ExecutionException, InterruptedException {
        return patientService.createPatient(patient);
    }

    @GetMapping("/get-patient/{patientId}")
    public Patient getPatient(@PathVariable String patientId) throws ExecutionException, InterruptedException {

        Patient patient = patientService.getPatient(patientId);
        if(patient != null){
            return patient;
            //display patient data on the web
        }else{
            return null;
            //display error message
        }
    }

    @PutMapping("/update-patient")
    public void updatePatient(@RequestBody Patient patient) throws ExecutionException, InterruptedException {
        patientService.updatePatient(patient);
    }

    @DeleteMapping("/delete-patient/{patientId}")
    public void deletePatient(@PathVariable String patientId) throws ExecutionException, InterruptedException {
        patientService.deletePatient(patientId);
    }

}
