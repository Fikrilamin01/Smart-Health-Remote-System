package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatus;
import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatusRepository;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.Prescription;
import com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription.PrescriptionRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PatientRepository {
    public static final String COL_NAME = "Patient";
    private final HealthStatusRepository healthStatusRepository;
    private final PrescriptionRepository prescriptionRepository;

    @Autowired
    public PatientRepository( HealthStatusRepository healthStatusRepository, PrescriptionRepository prescriptionRepository) {
        this.healthStatusRepository = healthStatusRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    //create and update function
    public String createPatient(Patient patient)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        //auto create data ID by firebase
        DocumentReference AddedDocRed = dbFirestore.collection(COL_NAME).document();
        patient.setUserId(AddedDocRed.getId());
        ApiFuture<WriteResult> collectionsApiFuture =AddedDocRed.set(patient);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String updatePatient(Patient patient)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture =
                dbFirestore.collection(COL_NAME).document(patient.getUserId()).set(patient);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }


    public static Patient getPatient(String patientId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(patientId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Patient tempPatient = null;
        if (document.exists()) {
            tempPatient  = document.toObject(Patient.class);
            return tempPatient;
        } else {
            return null;
        }
    }

    public String deletePatient(String patientId) throws ExecutionException, InterruptedException {
        List<HealthStatus> healthStatusList;
        List<Prescription> prescriptionList;
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(patientId).delete();
        return "Document with Patient Id " + patientId + " has been deleted and the files related to this patient";
    }
}
