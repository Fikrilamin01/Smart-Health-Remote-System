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
    private final DoctorService doctorService;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getPatientDashboard(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Patient patient = patientService.getPatient(myUserDetails.getUsername());
        Doctor doctor= doctorService.getDoctor(patient.getAssigned_doctor());
        List<Patient> patientList= patientService.getPatientList();

        model.addAttribute("patient",patient);
        model.addAttribute("doctor",doctor);
        model.addAttribute("patientList",patientList);

        return "patientDashBoard";
    }

    @GetMapping("/viewPrescription")
    public String getPrescription(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Patient patient = patientService.getPatient(myUserDetails.getUsername());
        Doctor doctor= doctorService.getDoctor(patient.getAssigned_doctor());
        Prescription prescription = patientService.getPrescription(myUserDetails.getUsername());

        model.addAttribute("patient",patient);
        model.addAttribute("doctor",doctor);
        model.addAttribute("prescription",prescription);

        return "viewPrescription";
    }


    @PostMapping("/backDashboard")
    public String backDashboard(@RequestParam (value = "patientId") String patientId, Model model) throws ExecutionException, InterruptedException {
        Patient patient=patientService.getPatient(patientId);
        Doctor doctor= doctorService.getDoctor(patient.getAssigned_doctor());
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        return "patientDashBoard";
    }

}
