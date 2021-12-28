package com.SmartHealthRemoteSystem.SHSR.ReadSensorData;

import com.SmartHealthRemoteSystem.SHSR.User.Patient.Patient;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientRepository;
import com.SmartHealthRemoteSystem.SHSR.User.Patient.PatientService;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ExecutionException;

@Repository
public class SensorDataRepository {
    public static final String COL_NAME = "SensorData";
    public final PatientRepository patientRepository;

    public SensorDataRepository(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;


    }


    public String CreateSensorData(SensorData sensorData)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();


        //auto create data ID by firebase
        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document();
        sensorData.setSensorDataId(addedDocRef.getId());
        ApiFuture<WriteResult> collectionsApiFuture =
                addedDocRef.set(sensorData);
        ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime());

        return addedDocRef.getId();
    }

    public String UpdateSensorData(SensorData sensorData)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        //auto create data ID by firebase


        DocumentReference addedDocRef = dbFirestore.collection(COL_NAME).document(sensorData.getSensorDataId());
        ApiFuture<WriteResult> collectionsApiFuture =
                dbFirestore.collection(COL_NAME).document(sensorData.getSensorDataId()).set(sensorData);
        ApiFuture<WriteResult> writeResult = addedDocRef.update("timestamp", collectionsApiFuture.get().getUpdateTime());
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    public SensorData getSensorDataDetails(String sensorDataId)
            throws InterruptedException, ExecutionException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(sensorDataId);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        SensorData tempSensorData = null;
        if (document.exists()) {
            tempSensorData = document.toObject(SensorData.class);
            return tempSensorData;
        } else {
            return null;
        }
    }

    public String deleteSensorData(String sensorDataId) {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> writeResult = dbFirestore.collection(COL_NAME).document(sensorDataId).delete();
        return "Document with Sensor Data Id " + sensorDataId + " has been deleted";
    }

    public void CreateTest(String PatientID)
    {   String URL= "test/kesh";
        // real-time database
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child(URL);

        ref.addValueEventListener(new ValueEventListener() {
            @SneakyThrows
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String sensorData = dataSnapshot.getValue().toString();
                SensorData test = new SensorData();
                test.setEcgReading(sensorData);
                String SensorDataID=CreateSensorData(test);
                Patient ahmad = patientRepository.getPatient(PatientID);




                if(ahmad.getSensorDataId().equals(""))
                {
                    ahmad.setSensorDataId(SensorDataID);
                    patientRepository.updatePatient(ahmad);
                }
                else
                {
                    String Sen_ID= ahmad.getSensorDataId();
                    test.setSensorDataId(Sen_ID);
                    UpdateSensorData(test);
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());

            }
        });
    }


}
