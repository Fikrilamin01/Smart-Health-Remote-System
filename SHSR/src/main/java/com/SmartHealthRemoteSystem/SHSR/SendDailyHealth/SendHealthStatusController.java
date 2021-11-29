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
    public void saveHealthStatus(@RequestBody HealthStatus healthStatus)
            throws ExecutionException, InterruptedException {
        healthStatusService.createHealthStatus(healthStatus);
    }

    @GetMapping("/get-health-status/{healthStatusId}")
    public HealthStatus getHealthStatus(@PathVariable String healthStatusId) throws ExecutionException, InterruptedException {

        HealthStatus healthStatus = healthStatusService.getHealthStatus(healthStatusId);
        if(healthStatus != null){
            return healthStatus;
            //display patient data on the web
        }else{
            return null;
            //display error message
        }
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
