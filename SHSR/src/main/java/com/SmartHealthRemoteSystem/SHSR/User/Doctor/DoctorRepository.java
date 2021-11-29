package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


import java.util.concurrent.ExecutionException;
@Repository
public class DoctorRepository {
    public static final String COL_NAME = "Doctor";

    public String createDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        //auto create data ID by firebase
        DocumentReference AddedDocRed = dbFirestore.collection(COL_NAME).document();
        doctor.setUserId(AddedDocRed.getId());
        ApiFuture<WriteResult> collectionsApiFuture =AddedDocRed.set(doctor);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String updateDoctor(Doctor doctor)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture =
                //auto create data ID by firebase
                dbFirestore.collection(COL_NAME).document(doctor.getUserId()).set(doctor);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }


    public static Doctor getDoctor(String doctorId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(doctorId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Doctor tempDoctor = null;
        if (document.exists()) {
            tempDoctor  = document.toObject(Doctor.class);
            return tempDoctor;
        } else {
            return null;
        }
    }

    public String deleteDoctor(String doctorId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(doctorId).delete();

        //deleting it from user table because it relates to that document/table
        //userRepository.deleteUser(doctorId);

        return "Document with Doctor Id " + doctorId + " has been deleted";
    }
}
