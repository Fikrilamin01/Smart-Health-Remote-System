package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import com.SmartHealthRemoteSystem.SHSR.User.User;

public class Patient extends User {
    private String address;
    private String emergencyContact;

    public Patient() {
    }

    public Patient(String name, String contact,String password, String address, String emergencyContact) {
        super(name,contact,password);
        this.address = address;
        this.emergencyContact = emergencyContact;
    }

    public Patient(String userId, String name, String contact, String password, String address, String emergencyContact) {
        super(userId, name, contact,password);
        this.address = address;
        this.emergencyContact = emergencyContact;
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
