package com.SmartHealthRemoteSystem.SHSR.ReadSensorData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class SensorDataService {
    private final SensorDataRepository sensorDataRepository;

    @Autowired
    public SensorDataService(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }

    public String createSensorData() throws ExecutionException, InterruptedException {
        SensorData sensorData = new SensorData();
        return sensorDataRepository.CreateSensorData(sensorData);
    }

    public String deleteSensorData(String sensorId){
        return sensorDataRepository.deleteSensorData(sensorId);
    }

    public SensorData getSensorData(String sensorId) throws ExecutionException, InterruptedException {
        return sensorDataRepository.getSensorDataDetails(sensorId);
    }

    public String updateSensorData(SensorData sensorData) throws ExecutionException, InterruptedException {
        return sensorDataRepository.UpdateSensorData(sensorData);
    }
}
