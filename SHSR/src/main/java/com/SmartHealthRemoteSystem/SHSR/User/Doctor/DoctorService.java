package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import com.SmartHealthRemoteSystem.SHSR.User.User;
import com.SmartHealthRemoteSystem.SHSR.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private UserService userService;
    private PatientRepository patientRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, UserService userService) {
        this.doctorRepository = doctorRepository;
        this.userService = userService;
    }

    public String createDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        //create a temporary user
        User user = new User(doctor.getUserId(),doctor.getName(),doctor.getPassword(),doctor.getContact(), doctor.getRole());
        if(userService.createUser(user)){
            //there is no conflict with the ID
            //proceed to create the patient in patient table
            String timeCreated = doctorRepository.saveDoctor(doctor);
            return timeCreated;
        }
        return "Failed to create user doctor with userId: " + doctor.getUserId();
    }

    public void updateDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        User user = new User(doctor.getUserId(),doctor.getName(),doctor.getPassword(),doctor.getContact(), doctor.getRole());
        String timeUpdate = userService.updateUser(user);
        String timeUpdate1 = doctorRepository.updateDoctor(doctor);
    }

    public Doctor getDoctor(String doctorId) throws ExecutionException, InterruptedException {
        Doctor doctor = doctorRepository.getDoctor(doctorId);
        if(doctor == null){
            return null;
        }else{
            User user = userService.getUser(doctorId);
            doctor.setName(user.getName());
            doctor.setPassword(user.getPassword());
            doctor.setContact(user.getContact());
            doctor.setRole(user.getRole());
            doctor.setUserId(user.getUserId());
            return doctor;
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

    public void deleteDoctor(String doctorId) throws ExecutionException, InterruptedException {
        String message = doctorRepository.deleteDoctor(doctorId);
        String timeDelete = userService.deleteUser(doctorId);
    }

    public List<Patient> findAllPatientAssignToDoctor(String doctorId) throws ExecutionException, InterruptedException {
        List<Patient> patientList = new ArrayList<>();
        //Logic on finding all the patient that has been assign to doctor...
        List<Patient> allPatientList = patientRepository.getListPatient();

        for (int i=0;i<patientList.size();i++){
            if(patientList.get(i).getAssigned_doctor().equals(doctorId)){
                patientList.add(patientList.get(i));
            }
        }
        return patientList;
    }

}
