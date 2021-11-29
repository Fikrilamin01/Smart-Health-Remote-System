package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void createDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        String timeCreated  = doctorRepository.createDoctor(doctor);
    }

    public void updateDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        String timeUpdated = doctorRepository.updateDoctor(doctor);
    }

    public Doctor getDoctor(String doctorId) throws ExecutionException, InterruptedException {
        Doctor doctor = doctorRepository.getDoctor(doctorId);
        if(doctor != null){
            return doctor;
        }else{
            return null;
        }
    }

    public Boolean doctorAuthentication (String doctorId,String password) throws ExecutionException, InterruptedException {
        Doctor doctor = getDoctor(doctorId);
        if(doctor == null){
            return false;
        }else{
            if(doctor.getPassword().equals(password)){
                return true;
            }else{
                return false;
            }
        }
    }


    public void deleteDoctor(String doctorId) {
        String message = doctorRepository.deleteDoctor(doctorId);
    }
}
