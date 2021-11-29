package com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/Prescription")
public class ViewPrescriptionController {
    private final PrescriptionService prescriptionService;

    @Autowired
    public ViewPrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @PostMapping("/create-prescription")
    public void savePrescription(@RequestBody Prescription prescription)
            throws ExecutionException, InterruptedException {
        String timeCreated = prescriptionService.createPrescription(prescription);
    }

    @GetMapping("/get-prescription/{prescriptionId}")
    public Prescription getPrescription(@PathVariable String prescriptionIdId) throws ExecutionException, InterruptedException {

        Prescription prescription = prescriptionService.getPrescription(prescriptionIdId);
        if(prescription != null){
            return prescription;
            //display patient data on the web
        }else{
            return null;
            //display error message
        }
    }

    @PutMapping("/update-prescription")
    public void updateSensorData(@RequestBody Prescription prescription) throws ExecutionException, InterruptedException {
        String timeUpdated = prescriptionService.updatePrescription(prescription);
    }

    @DeleteMapping("/delete-prescription/{prescriptionId}")
    public void deletePrescription(@PathVariable String prescriptionId) throws ExecutionException, InterruptedException {
        prescriptionService.deletePrescription(prescriptionId);
    }
}
