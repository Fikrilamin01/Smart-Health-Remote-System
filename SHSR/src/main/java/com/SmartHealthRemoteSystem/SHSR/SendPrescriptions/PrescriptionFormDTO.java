package com.SmartHealthRemoteSystem.SHSR.SendPrescriptions;

import java.io.Serializable;
import java.util.List;

public class PrescriptionFormDTO{
    String patientId;
    String doctorId;
    String prescription;
    String diagnosisAilment;
    List<String> medicine;


    public PrescriptionFormDTO(String patientId, String doctorId, String prescription, String diagnosisAilment, List<String> medicine) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.prescription = prescription;
        this.diagnosisAilment = diagnosisAilment;
        this.medicine = medicine;
    }

    public PrescriptionFormDTO() {
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

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDiagnosisAilment() {
        return diagnosisAilment;
    }

    public void setDiagnosisAilment(String diagnosisAilment) {
        this.diagnosisAilment = diagnosisAilment;
    }

    public List<String> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<String> medicine) {
        this.medicine = medicine;
    }

}
