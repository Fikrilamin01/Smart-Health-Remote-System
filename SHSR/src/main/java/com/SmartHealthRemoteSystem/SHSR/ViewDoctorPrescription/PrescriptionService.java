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

    public String createPrescription(Prescription prescription) throws ExecutionException, InterruptedException {
        return prescriptionRepository.CreatePrescription(prescription);
    }

    public Prescription getPrescription(String prescriptionIdId) throws ExecutionException, InterruptedException {
        return prescriptionRepository.getPrescription(prescriptionIdId);
    }
    public List<Prescription> getListPrescription() throws ExecutionException, InterruptedException {
        List<Prescription> prescriptionList = prescriptionRepository.getListPrescription();
        return  prescriptionList;
    }

    public void deleteAllPrescription(String userId) throws ExecutionException, InterruptedException {
        List<Prescription> prescriptionList = getListPrescription();
        String sensorDataId = "";
        for(Prescription prescription: prescriptionList){
            //deleting the patient prescription
            if(prescription.getPatientId().equals(userId)){
                prescriptionRepository.deletePrescription(prescription.getPrescriptionId());
                sensorDataId = prescription.getSensorDataId();
            }
        }
        //checking whether sensorData exist or not in database, if yes, deleted it.
        if(sensorDataService.getSensorData(sensorDataId) != null){
            sensorDataService.deleteSensorData(sensorDataId);
        }

    }


    public String updatePrescription(Prescription prescription) throws ExecutionException, InterruptedException {
        return prescriptionRepository.UpdatePrescription(prescription);
    }

    public void deletePrescription(String prescriptionId) throws ExecutionException, InterruptedException {
        String message = "";
        if(prescriptionRepository.getPrescription(prescriptionId) != null) {
            message = prescriptionRepository.deletePrescription(prescriptionId);
        }else{
            message = "Error, the prescription Id is not exist";
        }
    }
}
