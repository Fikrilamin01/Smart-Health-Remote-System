package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class HealthStatusService {

    private final HealthStatusRepository healthStatusRepository;
    private final SensorDataService sensorDataService;
   // private final PatientService patientService;

    @Autowired
    public HealthStatusService(HealthStatusRepository healthStatusRepository, SensorDataService sensorDataService) {
        this.healthStatusRepository = healthStatusRepository;
        this.sensorDataService = sensorDataService;
       // this.patientService = patientService;
    }

    public void createHealthStatus(HealthStatus healthStatus, String patientId) throws ExecutionException, InterruptedException {
        String timeCreated = healthStatusRepository.CreateHealthStatus(healthStatus,patientId);
    }

    public List<HealthStatus> getListHealthStatus(String patientId) throws ExecutionException, InterruptedException {
        return healthStatusRepository.getListHealthStatus(patientId);
    }

    public HealthStatus getHealthStatus(String healthStatusId, String patientId) throws ExecutionException, InterruptedException {
        return healthStatusRepository.getHealthStatus(healthStatusId, patientId);
    }

    public void deleteAllHealthStatus(String patientId) throws ExecutionException, InterruptedException {
        List<HealthStatus> healthStatusList = getListHealthStatus(patientId);
        for(HealthStatus healthStatus : healthStatusList){
            //deleting all health status in Database
            String deleteMessage = healthStatusRepository.deleteHealthStatus(healthStatus.getHealthStatusId(), patientId);
        }
    }

    public void updateHealthStatus(HealthStatus healthStatus, String patientId) throws ExecutionException, InterruptedException {
        String timeUpdated = healthStatusRepository.UpdateHealthStatus(healthStatus, patientId);
    }

    public void deleteHealthStatus(String healthStatusId, String patientId) throws ExecutionException, InterruptedException {
        String message = "";
        if(healthStatusRepository.getHealthStatus(healthStatusId, patientId) != null) {
            message = healthStatusRepository.deleteHealthStatus(healthStatusId, patientId);
        }else{
            message = "Error, the healthStatus Id is not exist";
        }
    }


}
