package com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class PrescriptionService {
    private final PrescriptionRepository prescriptionRepository;
    private final SensorDataService sensorDataService;

    @Autowired
    public PrescriptionService(PrescriptionRepository prescriptionRepository, SensorDataService sensorDataService) {
        this.prescriptionRepository = prescriptionRepository;
        this.sensorDataService = sensorDataService;
    }

    public String createPrescription(Prescription prescription, String patientId) throws ExecutionException, InterruptedException {
        return prescriptionRepository.CreatePrescription(prescription, patientId);
    }

    public Prescription getPrescription(String prescriptionIdId, String patientId) throws ExecutionException, InterruptedException {
        return prescriptionRepository.getPrescription(prescriptionIdId, patientId);
    }
    public List<Prescription> getListPrescription(String patientId) throws ExecutionException, InterruptedException {
        List<Prescription> prescriptionList = prescriptionRepository.getListPrescription(patientId);
        return  prescriptionList;
    }

    public void deleteAllPrescription(String patientId) throws ExecutionException, InterruptedException {
        List<Prescription> prescriptionList = getListPrescription(patientId);
        for(Prescription prescription: prescriptionList){
            //deleting the patient prescription
            prescriptionRepository.deletePrescription(prescription.getPrescriptionId(), patientId);
        }
    }


    public String updatePrescription(Prescription prescription, String patientId) throws ExecutionException, InterruptedException {
        return prescriptionRepository.UpdatePrescription(prescription, patientId);
    }

    public void deletePrescription(String prescriptionId, String patientId) throws ExecutionException, InterruptedException {
        String message = "";
        if(prescriptionRepository.getPrescription(prescriptionId, patientId) != null) {
            message = prescriptionRepository.deletePrescription(prescriptionId, patientId);
        }else{
            message = "Error, the prescription Id is not exist";
        }
    }
}
