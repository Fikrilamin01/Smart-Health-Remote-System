package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class HealthStatusService {

    private final HealthStatusRepository healthStatusRepository;
    private final SensorDataService sensorDataService;

    @Autowired
    public HealthStatusService(HealthStatusRepository healthStatusRepository, SensorDataService sensorDataService) {
        this.healthStatusRepository = healthStatusRepository;
        this.sensorDataService = sensorDataService;
    }

    public void createHealthStatus(HealthStatus healthStatus) throws ExecutionException, InterruptedException {
        //create object sensor data, only then can create health status
        String sensorDataId = sensorDataService.createSensorData();

        String timeCreated = healthStatusRepository.CreateHealthStatus(healthStatus,sensorDataId);
    }

    public List<HealthStatus> getListHealthStatus() throws ExecutionException, InterruptedException {
        List<HealthStatus> healthStatusList = healthStatusRepository.getListHealthStatus();
        return healthStatusList;
    }

    public HealthStatus getHealthStatus(String healthStatusId) throws ExecutionException, InterruptedException {
        HealthStatus healthStatus = healthStatusRepository.getHealthStatus(healthStatusId);
        if(healthStatus!=null){
            return healthStatus;
        }else {
            return null;
        }
    }

    public void deleteAllHealthStatus(String userId) throws ExecutionException, InterruptedException {
        List<HealthStatus> healthStatusList = getListHealthStatus();
        String sensorDataId = "";
        for(HealthStatus healthStatus : healthStatusList){
            if(healthStatus.getPatientId().equals(userId)){
                //deleting all health status in Database
                String deleteMessage = healthStatusRepository.deleteHealthStatus(healthStatus.getHealthStatusId());
                sensorDataId = healthStatus.getSensorDataId();
            }
        }
        // checking whether sensorData exist or not in database, if yes, deleted it.
        if(sensorDataService.getSensorData(sensorDataId) != null){
            sensorDataService.deleteSensorData(sensorDataId);
        }
    }

    public void updateHealthStatus(HealthStatus healthStatus) throws ExecutionException, InterruptedException {
        String timeUpdated = healthStatusRepository.UpdateHealthStatus(healthStatus);
    }

    public void deleteHealthStatus(String healthStatusId) throws ExecutionException, InterruptedException {
        String message = "";
        if(healthStatusRepository.getHealthStatus(healthStatusId) != null) {
            message = healthStatusRepository.deleteHealthStatus(healthStatusId);
        }else{
            message = "Error, the healthStatus Id is not exist";
        }
    }
}
