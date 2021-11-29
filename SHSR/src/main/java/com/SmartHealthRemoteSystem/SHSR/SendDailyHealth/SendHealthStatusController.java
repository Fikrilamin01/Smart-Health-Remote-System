package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/Health-status")
public class SendHealthStatusController {
    private final HealthStatusService healthStatusService;

    public SendHealthStatusController(HealthStatusService healthStatusService) {
        this.healthStatusService = healthStatusService;
    }

    @PostMapping("/create-health-status")
    public void saveHealthStatus(@RequestBody HealthStatus healthStatus, String patientId)
            throws ExecutionException, InterruptedException {
        healthStatusService.createHealthStatus(healthStatus, patientId);
    }

    @GetMapping("/get-health-status/{healthStatusId}")
    public HealthStatus getHealthStatus(@PathVariable String healthStatusId) throws ExecutionException, InterruptedException {

        return healthStatusService.getHealthStatus(healthStatusId);
    }

    @PutMapping("/update-health-status")
    public void updateHealthStatus(@RequestBody HealthStatus healthStatus) throws ExecutionException, InterruptedException {
        healthStatusService.updateHealthStatus(healthStatus);
    }

    @DeleteMapping("/delete-health-status/{healthStatusId}")
    public void deleteHealthStatus(@PathVariable String healthStatusId) throws ExecutionException, InterruptedException {
        healthStatusService.deleteHealthStatus(healthStatusId);
    }
}
