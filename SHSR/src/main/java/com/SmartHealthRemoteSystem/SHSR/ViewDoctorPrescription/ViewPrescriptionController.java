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

    @PostMapping("/create-prescription/{patientId}")
    public void savePrescription(@RequestBody Prescription prescription,@PathVariable String patientId)
            throws ExecutionException, InterruptedException {
        String timeCreated = prescriptionService.createPrescription(prescription, patientId);
    }

    @GetMapping("/get-prescription/{patientId}/{prescriptionId}")
    public Prescription getPrescription(@PathVariable String prescriptionId,@PathVariable String patientId) throws ExecutionException, InterruptedException {

        Prescription prescription = prescriptionService.getPrescription(prescriptionId, patientId);
        if(prescription != null){
            return prescription;
            //display patient data on the web
        }else{
            return null;
            //display error message
        }
    }

    @PutMapping("/update-prescription/{patientId}")
    public void updatePrescription(@RequestBody Prescription prescription, @PathVariable String patientId) throws ExecutionException, InterruptedException {
        String timeUpdated = prescriptionService.updatePrescription(prescription, patientId);
    }

    @DeleteMapping("/delete-prescription/{patientId}/{prescriptionId}")
    public void deletePrescription(@PathVariable String prescriptionId,@PathVariable String patientId) throws ExecutionException, InterruptedException {
        prescriptionService.deletePrescription(prescriptionId, patientId);
    }
}
