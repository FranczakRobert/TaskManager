package com.robert.googlefirestroredemo.Manager;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class FirebaseManager {

    private Firestore firestoreDB ;

    public FirebaseManager() {

        String pathToKey = "/Users/robertfranczak/Code/Java/GoogleFireStroreDemo/src/main/resources/security/key.json";
        try(FileInputStream serviceAccount = new FileInputStream(pathToKey)) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp.initializeApp(options);
            firestoreDB = FirestoreClient.getFirestore();

        } catch (IOException e) {
            System.out.println("Error reading key file");
        }
    }
}
