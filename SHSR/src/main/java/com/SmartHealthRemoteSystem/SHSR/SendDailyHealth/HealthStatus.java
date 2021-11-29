package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorData;

import java.sql.Timestamp;


public class HealthStatus {
    //add health status Id to prevent conflict in database
    private String healthStatusId;
    private String sensorDataId;
    private String additionalNotes;
    private String patientId;
    private String doctorId;
    private String timestamp;

    public HealthStatus() {
    }

    public HealthStatus(String additionalNotes, String patientId, String doctorId) {
        this.additionalNotes = additionalNotes;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    public HealthStatus(String healthStatusId, String sensorDataId, String additionalNotes, String patientId, String doctorId, String  timestamp) {
        this.healthStatusId = healthStatusId;
        this.sensorDataId = sensorDataId;
        this.additionalNotes = additionalNotes;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.timestamp = timestamp;
    }

    public String getHealthStatusId() {
        return healthStatusId;
    }

    public void setHealthStatusId(String healthStatusId) {
        this.healthStatusId = healthStatusId;
    }

    public String getSensorDataId() {
        return sensorDataId;
    }

    public void setSensorDataId(String sensorDataId) {
        this.sensorDataId = sensorDataId;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
