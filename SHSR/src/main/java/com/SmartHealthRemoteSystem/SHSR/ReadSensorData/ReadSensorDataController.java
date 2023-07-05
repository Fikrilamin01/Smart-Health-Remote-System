package com.SmartHealthRemoteSystem.SHSR.ReadSensorData;

import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatus;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/Sensor-data")
public class ReadSensorDataController {
    private final SensorDataService sensorDataService;

    @Autowired
    public ReadSensorDataController(SensorDataService sensorDataService) {
        this.sensorDataService = sensorDataService;
    }

    @PostMapping("/create-sensor-data")
    public void saveSensorData(@RequestBody SensorData sensorData)
            throws ExecutionException, InterruptedException {
    }

    @GetMapping("/get-sensor-data/{sensorDataId}")
    public SensorData getHealthStatus(@PathVariable String sensorDataId) throws ExecutionException, InterruptedException {

        SensorData sensorData = sensorDataService.getSensorData(sensorDataId);
        if(sensorData != null){
            return sensorData;
            //display patient data on the web
        }else{
            return null;
            //display error message
        }
    }

    @PutMapping("/update-sensor-data")
    public void updateSensorData(@RequestBody SensorData sensorData) throws ExecutionException, InterruptedException {
    }

    @DeleteMapping("/delete-sensor-data/{sensorDataId}")
    public void deleteHealthStatus(@PathVariable String sensorDataId) throws ExecutionException, InterruptedException {
        sensorDataService.deleteSensorData(sensorDataId);
    }
}
