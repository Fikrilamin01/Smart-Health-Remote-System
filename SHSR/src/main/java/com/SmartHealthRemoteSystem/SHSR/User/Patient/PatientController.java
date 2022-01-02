package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorService;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.Prescription;
import com.SmartHealthRemoteSystem.SHSR.WebConfiguration.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String getPatientDashboard(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Patient patient = patientService.getPatient(myUserDetails.getUsername());
        Doctor doctor = patientService.findDoctorThroughHealthStatusPatient(patient);
        List<Patient> patientList= patientService.getPatientList(myUserDetails.getUsername());

        model.addAttribute("patient",patient);
        model.addAttribute("doctor",doctor);
        model.addAttribute("patientList",patientList);

        return "patientDashBoard";
    }

    @GetMapping("/viewPrescription")
    public String getPatientListThatAssignedToDoctor(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Patient patient = patientService.getPatient(myUserDetails.getUsername());
        Doctor doctor = patientService.findDoctorThroughHealthStatusPatient(patient);
        Prescription prescription = patientService.getPrescription(myUserDetails.getUsername());

        model.addAttribute("patient",patient);
        model.addAttribute("doctor",doctor);
        model.addAttribute("prescription",prescription);

        return "viewPrescription";
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
