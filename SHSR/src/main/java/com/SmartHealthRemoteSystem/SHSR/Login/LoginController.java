package com.SmartHealthRemoteSystem.SHSR.Login;

import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorService;

import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String verification() {
        return "index";
    }

    @GetMapping("/doctor/dashboard")
    public String getDoctorDashboard(){
        return "doctorDashBoard";
    }

    @GetMapping("/patient/dashboard")
    public String getPatientDashboard(){
        return "patientDashBoard";
    }
}

