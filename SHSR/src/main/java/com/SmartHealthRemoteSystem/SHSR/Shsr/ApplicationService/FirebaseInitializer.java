package com.SmartHealthRemoteSystem.SHSR.Shsr.ApplicationService;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FirebaseInitializer {

    @PostConstruct
    private void initDB() throws IOException {
        InputStream serviceAccount = this.getClass().getClassLoader().getResourceAsStream("./smarthealthcaresupport-firebase-adminsdk-5jy1z-6e2ef548bb.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://smarthealthcaresupport-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();
        if(FirebaseApp.getApps().isEmpty()){
            FirebaseApp.initializeApp(options);
        }
    }

    public Firestore getFirebase(){
        return FirestoreClient.getFirestore();
    }
}
