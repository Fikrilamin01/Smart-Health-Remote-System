package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import com.SmartHealthRemoteSystem.SHSR.WebConfiguration.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RequestMapping("/doctor")
@Controller
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getDoctorDashboard(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails= (MyUserDetails) auth.getPrincipal();
        Doctor doctor = doctorService.getDoctor(myUserDetails.getUsername());
        model.addAttribute("doctor",doctor);
        return "doctorDashBoard";
    }

    @GetMapping("/myPatient")
    public String getPatientListThatAssignedToDoctor(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails= (MyUserDetails) auth.getPrincipal();
        Doctor doctor = doctorService.getDoctor(myUserDetails.getUsername());
        List<Patient> patientList = doctorService.findAllPatientAssignToDoctor(doctor.getUserId());
        model.addAttribute("patientList", patientList);
        model.addAttribute("doctor", doctor);
        return "myPatient";
    }

}
