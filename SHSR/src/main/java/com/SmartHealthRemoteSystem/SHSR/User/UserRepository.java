package com.SmartHealthRemoteSystem.SHSR.User;

import com.SmartHealthRemoteSystem.SHSR.Repository.SHSRDAO;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
public class UserRepository implements SHSRDAO<User> {
    public final String COL_NAME = "User";

//    @SneakyThrows
//    public User getUser(String UserId)  {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(UserId);
//        ApiFuture<DocumentSnapshot> future = documentReference.get();
//        DocumentSnapshot document = future.get();
//        User user;
//        user = document.toObject(User.class);
//        return user;
//    }
//
//    //Create or update user
//    public String saveUser(User user)
//            throws InterruptedException, ExecutionException {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getUserId()).set(user);
//        return collectionsApiFuture.get().getUpdateTime().toString();
//    }
//
//    public List<User> getListUser()
//            throws InterruptedException, ExecutionException {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).listDocuments();
//        Iterator<DocumentReference> iterator = documentReference.iterator();
//
//        List<User> userList = new ArrayList<>();
//        User user = null;
//        while (iterator.hasNext()) {
//            DocumentReference documentReference1=iterator.next();
//            ApiFuture<DocumentSnapshot> future = documentReference1.get();
//            DocumentSnapshot document = future.get();
//            user = document.toObject(User.class);
//            userList.add(user);
//        }
//        return userList;
//    }
//
//    public String deleteUser(String userId) throws ExecutionException, InterruptedException {
//        Firestore dbFirestore = FirestoreClient.getFirestore();
//        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(userId).delete();
//        return collectionsApiFuture.get().getUpdateTime().toString();
//    }

    @Override
    public User get(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        DocumentReference documentReference = dbFirestore.collection(COL_NAME).document(id);
        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();
        User user;
        user = document.toObject(User.class);
        return user;
    }

    @Override
    public List<User> getAll() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Iterable<DocumentReference> documentReference = dbFirestore.collection(COL_NAME).listDocuments();
        Iterator<DocumentReference> iterator = documentReference.iterator();

        List<User> userList = new ArrayList<>();
        User user;
        while (iterator.hasNext()) {
            DocumentReference documentReference1=iterator.next();
            ApiFuture<DocumentSnapshot> future = documentReference1.get();
            DocumentSnapshot document = future.get();
            user = document.toObject(User.class);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public String save(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getUserId()).set(user);
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

    @Override
    public String update(User user) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        Map<String, String> tempUser = new HashMap<>();
        ApiFuture<WriteResult> collectionsApiFuture = null;
        //if-else condition is added to check which field does the user update.
        //statement inside if-else condition will add change value into a map key value
        //map key value will be pass to firestore database to be updated.
        if(!(user.getName().isEmpty())){
            collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getUserId()).update("name", user.getName());
        }else if(!(user.getPassword().isEmpty())){
            collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getUserId()).update("password", user.getPassword());
        } else if(!(user.getContact().isEmpty())){
            collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getUserId()).update("contact", user.getContact());
        } else if(!(user.getRole().isEmpty())){
            collectionsApiFuture = dbFirestore.collection(COL_NAME).document(user.getUserId()).update("role", user.getRole());
        }
        if (collectionsApiFuture != null) {
            return collectionsApiFuture.get().getUpdateTime().toString();
        } else{
            return Timestamp.now().toString();
        }
    }

    @Override
    public String delete(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionsApiFuture = dbFirestore.collection(COL_NAME).document(id).delete();
        return collectionsApiFuture.get().getUpdateTime().toString();
    }

}
