package com.example.MainWindow;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class DataService {

    private static Firestore db;

    static{
        try{
            initializeFirebase();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    private static void initializeFirebase() throws IOException{
        FileInputStream serviceAccount = new FileInputStream("tripplanner\\src\\main\\resources\\ServiceAccountKey.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
        .build();

        FirebaseApp.initializeApp(options);
        db = FirestoreClient.getFirestore();
    }

    public void addData(String collection, String document,Map<String,Object> data) throws ExecutionException,InterruptedException{
        DocumentReference docRef = db.collection(collection).document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public DocumentSnapshot getData(String collection,String document) throws ExecutionException,InterruptedException{
        try{
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            return future.get();
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public boolean authenticateUser(String username,String password) throws ExecutionException,InterruptedException{
        DocumentSnapshot document = db.collection("users").document(username).get().get();
        if (document.exists()){
            String storedPassword = document.getString("password");
            
            return password.equals(storedPassword);
        }
        return false;
    } 
}
