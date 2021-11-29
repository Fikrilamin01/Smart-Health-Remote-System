package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/Doctor")
public class DoctorController {
    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping("/create-doctor")
    public void saveDoctor(@RequestBody Doctor doctor)
            throws ExecutionException, InterruptedException {
        doctorService.createDoctor(doctor);
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
