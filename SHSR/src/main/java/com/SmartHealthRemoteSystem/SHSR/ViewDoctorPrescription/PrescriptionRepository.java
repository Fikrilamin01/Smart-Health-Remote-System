package com.SmartHealthRemoteSystem.SHSR.ViewDoctorPrescription;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class PrescriptionRepository {

    public static final String COL_NAME = "Prescription";

    public String CreatePrescription(Prescription prescription)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        //auto create data ID by firebase
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document();
        prescription.setPrescriptionId(addedDocRef.getId());
        ApiFuture<WriteResult> collectionsApiFuture =
                addedDocRef.set(prescription);
        ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime().toString());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public String UpdatePrescription(Prescription prescription)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document(prescription.getPrescriptionId());
        ApiFuture<WriteResult> collectionsApiFuture =
                //auto create data ID by firebase
                addedDocRef.set(prescription);
        ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime().toString());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public Prescription getPrescription(String prescriptionId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(prescriptionId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        Prescription tempPrescription = null;
        if (document.exists()) {
            tempPrescription = document.toObject(Prescription.class);
            return tempPrescription;
        } else {
            return null;
        }
    }

    public List<Prescription> getListPrescription()
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<Prescription> prescriptionList = new ArrayList<>();
        Prescription prescription = null;
        while (iterator.hasNext()) {
            DocumentReference documentReference1=iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            prescription = document.toObject(Prescription.class);
            prescriptionList.add(prescription);
        }

        return prescriptionList;
    }

    public String deletePrescription(String prescriptionId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(prescriptionId).delete();
        return "Document with Sensor Data Id " + prescriptionId + " has been deleted";
    }
}
