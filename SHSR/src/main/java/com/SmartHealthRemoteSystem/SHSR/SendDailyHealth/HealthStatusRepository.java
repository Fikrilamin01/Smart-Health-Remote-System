package com.SmartHealthRemoteSystem.SHSR.SendDailyHealth;

import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorData;
import com.SmartHealthRemoteSystem.SHSR.ReadSensorData.SensorDataRepository;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


@Repository
public class HealthStatusRepository {
    private final SensorDataRepository sensorDataRepository;
    public static final String COL_NAME = "Patient";
    public static final String SUB_COL_NAME = "HealthStatus";

    @Autowired
    public HealthStatusRepository(SensorDataRepository sensorDataRepository) {
        this.sensorDataRepository = sensorDataRepository;
    }



    public String CreateHealthStatus(HealthStatus healthStatus, String patientId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        //auto create data ID by firebase
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document(patientId).collection(SUB_COL_NAME).document();
        healthStatus.setHealthStatusId(addedDocRef.getId());
        ApiFuture<WriteResult> collectionsApiFuture =
                addedDocRef.set(healthStatus);
        ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime().toString());

        return collectionsApiFuture.get().getUpdateTime().toString();

    }

    public String UpdateHealthStatus(HealthStatus healthStatus, String patientId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document(patientId).collection(SUB_COL_NAME).document(healthStatus.getHealthStatusId());
        ApiFuture<WriteResult> collectionsApiFuture =
                addedDocRef.set(healthStatus);
        ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime().toString());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public HealthStatus getHealthStatus(String healthStatusId, String patientId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(patientId).collection(SUB_COL_NAME).document(healthStatusId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        HealthStatus tempHealthStatus = null;
        if (document.exists()) {
            tempHealthStatus = document.toObject(HealthStatus.class);
            return tempHealthStatus;
        } else {
            return null;
        }
    }

    public List<HealthStatus> getListHealthStatus(String patientId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).document(patientId).collection(SUB_COL_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<HealthStatus> healthStatusList = new ArrayList<>();
        HealthStatus healthStatus = null;
        while (iterator.hasNext()) {
            DocumentReference documentReference1=iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            healthStatus = document.toObject(HealthStatus.class);
            healthStatusList.add(healthStatus);
        }

        return healthStatusList;
    }


    public String deleteHealthStatus(String healthStatusId, String patientId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document(patientId).collection(SUB_COL_NAME).document(healthStatusId);
        ApiFuture<WriteResult> writeResult = addedDocRef.delete();

        return "Document with Health Id " + healthStatusId + " has been deleted";
    }


}
