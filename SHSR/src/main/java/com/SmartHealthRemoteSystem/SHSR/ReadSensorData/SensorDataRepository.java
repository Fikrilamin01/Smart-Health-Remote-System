package com.SmartHealthRemoteSystem.SHSR.ReadSensorData;

import com.SmartHealthRemoteSystem.SHSR.Repository.SHSRDAO;
import com.SmartHealthRemoteSystem.SHSR.Repository.SubCollectionSHSRDAO;
import com.SmartHealthRemoteSystem.SHSR.User.Doctor.Doctor;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import com.SmartHealthRemoteSystem.SHSR.User.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public class SensorDataRepository implements SHSRDAO<SensorData> {
    private final String COL_NAME = "SensorData";

    @Override
    public SensorData get(String sensorDataId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(sensorDataId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        SensorData tempSensorData;
        if (document.exists()) {
            tempSensorData = document.toObject(SensorData.class);
            return tempSensorData;
        } else {
            return null;
        }
    }

    @Override
    public List<SensorData> getAll() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<SensorData> sensorDataList = new ArrayList<>();
        SensorData sensorData;
        while (iterator.hasNext()) {
            DocumentReference documentReference1=iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            sensorData = document.toObject(SensorData.class);
            sensorDataList.add(sensorData);
        }

        return sensorDataList;
    }

    @Override
    public String save(SensorData sensorData) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        //auto create data ID by firebase
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document();
        sensorData.setSensorDataId(addedDocRef.getId());
        ApiFuture<WriteResult> collectionsApiFuture =
                addedDocRef.set(sensorData);
         addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime());

        return addedDocRef.getId();
    }

    @Override
    public String update(SensorData sensorData) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document(sensorData.getSensorDataId());
        ApiFuture<WriteResult> collectionsApiFuture = null;
        if(!(sensorData.getEcgReading().isEmpty())){
            collectionsApiFuture =addedDocRef.update("ecgReading", sensorData.getEcgReading());
        }
        if (!(sensorData.getOxygenReading().isEmpty())){
            collectionsApiFuture =  addedDocRef.update("oxygenReading", sensorData.getOxygenReading());
        }
        if (sensorData.getBodyTemperature() != null){
            collectionsApiFuture = addedDocRef.update("bodyTemperature", sensorData.getBodyTemperature());
        }
        if (collectionsApiFuture != null) {
            ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime());
            return writeResult.get().getUpdateTime().toString();
        }
        return Timestamp.now().toString();
    }

    @Override
    public String delete(String sensorDataId) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        if(get(sensorDataId) == null){
            return "The sensorData with Id " + sensorDataId + " is not exist.";
        }
         dbFirestore.collection(COL_NAME).document(sensorDataId).delete();
        return "Document with Sensor Data Id " + sensorDataId + " has been deleted";
    }
}
