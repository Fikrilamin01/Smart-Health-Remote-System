package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorData;
import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataRepository;
import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
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
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
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
        List<Patient> patientList= doctorService.getListPatient();
        model.addAttribute("patientList", patientList);
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

    @GetMapping("/sensorDashboard")
    public String getSensorDashboard(Model model, @RequestParam(value= "patientId") String patientId) throws Exception {

        Patient patient = doctorService.getPatient(patientId);
        SensorDataService sensorDataService = new SensorDataService();
        SensorData sensorData= sensorDataService.getSensorData(patient.getSensorDataId());
        model.addAttribute("sensorDataList",sensorData);
        return "sensorDashboard";
    }

    @PostMapping("/create-doctor")
    public String saveDoctor(@RequestBody Doctor doctor)
            throws ExecutionException, InterruptedException {
       String msg = doctorService.createDoctor(doctor);
       return msg;
    }

    @GetMapping("/get-doctor/{doctorId}")
    public Doctor getDoctor(@PathVariable String doctorId) throws ExecutionException, InterruptedException {

        Doctor doctor = doctorService.getDoctor(doctorId);
        if(doctor != null){
            return doctor;
            //display patient data on the web
        }else{
            return null;
            //display error message
        }
    }

    @PutMapping("/update-doctor")
    public void updateDoctor(@RequestBody Doctor doctor) throws ExecutionException, InterruptedException {
        doctorService.updateDoctor(doctor);
    }

    @DeleteMapping("/delete-doctor/{doctorId}")
    public void deleteDoctor(@PathVariable String doctorId) throws ExecutionException, InterruptedException {
        doctorService.deleteDoctor(doctorId);
    }
}
