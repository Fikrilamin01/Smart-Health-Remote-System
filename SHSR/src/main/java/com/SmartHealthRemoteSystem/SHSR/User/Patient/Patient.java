package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import com.SmartHealthRemoteSystem.SHSR.User.User;

public class Patient extends User {
    private String sensorDataId;
    private String address;
    private String emergencyContact;

    public Patient() {
    }

    public Patient(String userId, String name, String password, String contact, String sensorDataId, String address, String emergencyContact) {
        super(userId, name, password, contact);
        this.sensorDataId = sensorDataId;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    public Patient(String name, String password, String contact, String sensorDataId, String address, String emergencyContact) {
        super(name, password, contact);
        this.sensorDataId = sensorDataId;
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    public String getSensorDataId() {
        return sensorDataId;
    }

    public void setSensorDataId(String sensorDataId) {
        this.sensorDataId = sensorDataId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmergencyContact() {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact) {
        this.emergencyContact = emergencyContact;
    }
}
