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

    private final DoctorService doctorService;
    private final PatientService patientService;

    @Autowired
    public LoginController(DoctorService doctorService, PatientService patientService) {
        this.doctorService = doctorService;
        this.patientService = patientService;
    }


    @GetMapping("/login")
    public String verification(
            @RequestParam(value = "uid") String userId,
            @RequestParam(value="pswd") String password,
            @RequestParam(value="userType") String userType,Model model)
            throws InterruptedException, ExecutionException  {
        if(userType.equals("doctor")){
            if(doctorService.doctorAuthentication(userId, password) == true){
                //Retrieve the doctor object info and return it to doctor dashboard
                model.addAttribute("Doctor",doctorService.getDoctor(userId));
                return "doctorDashBoard";
            }
        }else{
            if(patientService.patientAuthentication(userId, password) == true){
                //Retrieve the doctor object info and return it to doctor dashboard
                model.addAttribute("Patient",patientService.getPatient(userId));
                return "patientDashBoard";
            }
        }
        //return error message
        model.addAttribute("InvalidCredential",true);
        return "index";

//        if(DoctorRepository.getDoctor(request.getParameter("uid"))!= null && DoctorRepository.getDoctor(request.getParameter("pswd")) !=null){
//            return "doctorDashBoard.html";
//        }else if (PatientRepository.getPatient(request.getParameter("uid")) != null &&  PatientRepository.getPatient(request.getParameter("pswd")) !=null ){
//            return "patientDashBoard.html";
//        }else {
//            return "login.html";
//        }
    }
}
