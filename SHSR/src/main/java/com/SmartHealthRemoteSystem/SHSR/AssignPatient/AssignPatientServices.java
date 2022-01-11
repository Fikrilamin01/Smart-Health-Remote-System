package com.SmartHealthRemoteSystem.SHSR.AssignPatient;

import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientRepository;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class AssignPatientServices {
    public final PatientRepository patientRepository;
    public final DoctorRepository doctorRepository;


    public AssignPatientServices(PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;

    }
    public List<Patient> getListPatient() throws ExecutionException, InterruptedException {
        //function to return list of unassigned patient
        List<Patient> patients=patientRepository.getListPatient();
        for(int i=patients.size()-1;i>=0;i--)
        {
           if (!(patients.get(i).getAssigned_doctor().isEmpty())){
                patients.remove(i);
           }
        }



        return patients;

    }
    public Doctor getDoctor(String drID) throws ExecutionException, InterruptedException {

        Doctor doctor=doctorRepository.getDoctor(drID);
        return doctor;
    }
    public Patient getPatient(String patientID) throws ExecutionException, InterruptedException {
        Patient patient=patientRepository.getPatient(patientID);
        return patient;
    }
    public void AssignPatient(String patientId,String doctorId) throws ExecutionException, InterruptedException {
        Doctor doctor=doctorRepository.getDoctor(doctorId);
        Patient patient=patientRepository.getPatient(patientId);
        patient.setAssigned_doctor(doctorId);
        patientRepository.updatePatient(patient);
    }
    public void UnassignDoctor(String patientId,String doctorId) throws ExecutionException, InterruptedException {
        Doctor doctor=doctorRepository.getDoctor(doctorId);
        Patient patient=patientRepository.getPatient(patientId);
        patient.setAssigned_doctor("");
        patientRepository.updatePatient(patient);
    }
}
