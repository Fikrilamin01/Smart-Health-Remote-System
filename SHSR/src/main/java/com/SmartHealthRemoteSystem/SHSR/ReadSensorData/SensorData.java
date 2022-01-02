package com.SmartHealthRemoteSystem.SHSR.ReadSensorData;

import com.google.cloud.Timestamp;

import java.sql.Time;

public class    SensorData {
    private String ecgReading;
    private double bodyTemperature;
    private Timestamp timestamp;
    private String sensorDataId;
    private String oxygenReading;


    public SensorData() {
    }

    public SensorData(String ecgReading, double bodyTemperature, String oxygenReading) {
        this.ecgReading = ecgReading;
        this.bodyTemperature = bodyTemperature;
        this.oxygenReading = oxygenReading;
    }

    public SensorData(String ecgReading, double bodyTemperature, Timestamp timestamp, String sensorDataId, String OxygenReading) {
        this.ecgReading = ecgReading;
        this.bodyTemperature = bodyTemperature;
        this.timestamp = timestamp;
        this.sensorDataId = sensorDataId;
        this.oxygenReading= OxygenReading;
    }

    public String getEcgReading() {
        return ecgReading;
    }

    public void setEcgReading(String ecgReading) {
        this.ecgReading = ecgReading;
    }

    public double getBodyTemperature() {
        return bodyTemperature;
    }

    public void setBodyTemperature(double bodyTemperature) {
        this.bodyTemperature = bodyTemperature;
    }

    public void setOxygenReading(String OxygenReading){this.oxygenReading=OxygenReading;}

    public String getOxygenReading(){return oxygenReading;}

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getSensorDataId() {
        return sensorDataId;
    }

    public void setSensorDataId(String sensorDataId) {
        this.sensorDataId = sensorDataId;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "ecgReading='" + ecgReading + '\'' +
                "\n timestamp=" + timestamp +
                "\n sensorDataId='" + sensorDataId + '\'' +
                '}';
    }
}
