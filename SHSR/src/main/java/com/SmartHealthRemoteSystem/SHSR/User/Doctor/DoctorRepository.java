package com.SmartHealthRemoteSystem.SHSR.User.Doctor;

import com.SmartHealthRemoteSystem.SHSR.SendDailyHealth.HealthStatus;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
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


import java.util.*;
import java.util.concurrent.ExecutionException;
@Repository
public class DoctorRepository {
    public static final String COL_NAME = "Doctor";

    //Create or update doctor
    public String saveDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Map<String, String> tempDoctor = new HashMap<>();
        tempDoctor.put("hospital", doctor.getHospital());
        tempDoctor.put("position", doctor.getPosition());

        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(doctor.getUserId()).set(tempDoctor);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String updateDoctor(Doctor doctor) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME)
                .document(doctor.getUserId())
                .update("hospital", doctor.getHospital(),
                        "position", doctor.getPosition());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }



    public Doctor getDoctor(String doctorId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(doctorId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Doctor tempDoctor;
        if (document.exists()) {
            tempDoctor  = document.toObject(Doctor.class);
            return tempDoctor;
        } else {
            return null;
        }
    }

    public List<Doctor> getListDoctor()
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<Doctor> doctorList = new ArrayList<>();
        Doctor doctor = null;
        while (iterator.hasNext()) {
            DocumentReference documentReference1=iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            doctor = document.toObject(Doctor.class);
            doctorList.add(doctor);
        }

        return doctorList;
    }

    public String deleteDoctor(String doctorId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(doctorId).delete();
        return "Document with Doctor Id " + doctorId + " has been deleted";
    }
}
