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

//        model.addAttribute("doctor",doctor);
        return "doctorDashBoard";
    }

    @GetMapping("/patient/dashboard")
    public String getPatientDashboard(Model model){
        model.addAllAttributes("patient,doctor",{patient,doctor});
//        model.addAttribute("patient",patient);
        return "patientDashBoard";
    }
}

