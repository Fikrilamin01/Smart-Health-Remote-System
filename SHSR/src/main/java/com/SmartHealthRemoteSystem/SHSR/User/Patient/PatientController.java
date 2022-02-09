package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.DoctorService;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.Prescription;
import com.SmartHealthRemoteSystem.SHSR.WebConfiguration.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import com.google.cloud.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/patient")
public class PatientController {
    private final PatientService patientService;
    private final DoctorService doctorService;

    @Autowired
    public PatientController(PatientService patientService, DoctorService doctorService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getPatientDashboard(Model model) throws ExecutionException, InterruptedException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Patient patient = patientService.getPatient(myUserDetails.getUsername());
        Doctor doctor = doctorService.getDoctor(patient.getAssigned_doctor());
        List<Patient> patientList = patientService.getPatientList();

        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("patientList", patientList);

        return "patientDashBoard";
    }

    @GetMapping("/viewPrescription")
    public String getPatientListThatAssignedToDoctor(Model model, @RequestParam(value = "pageNo") int pageNo) throws ExecutionException, InterruptedException {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails myUserDetails = (MyUserDetails) auth.getPrincipal();
        Patient patient = patientService.getPatient(myUserDetails.getUsername());
        Doctor doctor = doctorService.getDoctor(patient.getAssigned_doctor());
        ArrayList<Prescription> prescriptionTemp = (ArrayList<Prescription>) patientService.getAllPrescription(patient.getUserId());

        ArrayList<Prescription> prescriptionList = quickSort(prescriptionTemp);

        Prescription prescription = null;
        if ((!prescriptionList.isEmpty()) && pageNo == -1) {
            prescription = prescriptionList.get(prescriptionList.size() - 1);
            pageNo = prescriptionList.size() - 1;
        } else if (!prescriptionList.isEmpty()) {
            prescription = prescriptionList.get(pageNo);
        }
        int totalPage = prescriptionList.size();

        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        model.addAttribute("prescription", prescription);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPage", totalPage);

        return "viewPrescription";
    }

    @PostMapping("/backDashboard")
    public String backDashboard(@RequestParam(value = "patientId") String patientId, Model model) throws ExecutionException, InterruptedException {
        Patient patient = patientService.getPatient(patientId);
        Doctor doctor = doctorService.getDoctor(patient.getAssigned_doctor());
        model.addAttribute("patient", patient);
        model.addAttribute("doctor", doctor);
        return "patientDashBoard";
    }

    public ArrayList<Prescription> quickSort(ArrayList<Prescription> list)
    {
        if (list.isEmpty())
            return list;
        ArrayList<Prescription> sorted;
        ArrayList<Prescription> smaller = new ArrayList<Prescription>();
        ArrayList<Prescription> greater = new ArrayList<Prescription>();
        Prescription pivot = list.get(0);
        int i;
        Prescription j;
        for (i=1;i<list.size();i++)
        {
            j=list.get(i);
            if (compare(j,pivot)<0)
                smaller.add(j);
            else
                greater.add(j);
        }
        smaller=quickSort(smaller);
        greater=quickSort(greater);
        smaller.add(pivot);
        smaller.addAll(greater);
        sorted = smaller;

        return sorted;
    }

    int compare(Prescription obj1, Prescription obj2){
        String ts1 = String.valueOf(obj1.getTimestamp());
        String ts2 = String.valueOf(obj2.getTimestamp());
        Timestamp timestamp1 = Timestamp.parseTimestamp(ts1);
        Timestamp timestamp2 = Timestamp.parseTimestamp(ts2);
        return timestamp1.compareTo(timestamp2);

    }

}
