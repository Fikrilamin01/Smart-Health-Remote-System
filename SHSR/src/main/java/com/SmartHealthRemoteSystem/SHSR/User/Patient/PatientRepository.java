package com.SmartHealthRemoteSystem.SHSR.User.Patient;

import com.SmartHealthRemoteSystem.SHSR.User.User;
import com.SmartHealthRemoteSystem.SHSR.User.UserRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class PatientRepository {
    public static final String COL_NAME = "Patient";
    public final UserRepository userRepository;

    public PatientRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String savePatient(Patient patient)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Map<String, String> tempPatient = new HashMap<>();
        tempPatient.put("address", patient.getAddress());
        tempPatient.put("emergencyContact", patient.getEmergencyContact());
        tempPatient.put("sensorDataId", patient.getSensorDataId());
        //Create a temporary User
        User user = new User(patient.getUserId(), patient.getName(), patient.getPassword(), patient.getContact(), patient.getRole());
        userRepository.saveUser(user);

        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(patient.getUserId()).set(tempPatient);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String updatePatient(Patient patient) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME)
                .document(patient.getUserId())
                .update("address", patient.getAddress(),
                        "emergencyContact", patient.getEmergencyContact(),
                        "sensorDataId", patient.getSensorDataId());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }




    public Patient getPatient(String patientId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(patientId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Patient tempPatient;
        if (document.exists()) {
            tempPatient  = document.toObject(Patient.class);
            User user = userRepository.getUser(patientId);
            tempPatient.setUserId(user.getUserId());
            tempPatient.setName(user.getName());
            tempPatient.setPassword(user.getPassword());
            tempPatient.setContact(user.getContact());
            tempPatient.setRole(user.getRole());
            return tempPatient;
        } else {
            return null;
        }
    }

    public List<Patient> getListPatient()
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<Patient> patientList = new ArrayList<>();
        Patient patient= null;
        while (iterator.hasNext()) {
            DocumentReference documentReference1=iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            patient = document.toObject(Patient.class);
            patientList.add(patient);
        }

        return patientList;
    }

    public String deletePatient(String patientId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(patientId).delete();
        return "Document with Patient Id " + patientId + " has been deleted and the files related to this patient";
    }
}
