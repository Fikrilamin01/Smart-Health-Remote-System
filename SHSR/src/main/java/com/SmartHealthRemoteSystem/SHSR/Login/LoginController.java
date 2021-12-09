package com.SmartHealthRemoteSystem.SHSR.Login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/doctor/dashboard")
    public String getDoctorDashboard(Model model){

//        model.addAllAttributes("doctor",doctor);
        return "doctorDashBoard";
    }

    @GetMapping("/patient/dashboard")
    public String getPatientDashboard(){
        return "patientDashBoard";
    }
}

