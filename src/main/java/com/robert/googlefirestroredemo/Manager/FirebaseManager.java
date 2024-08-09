package com.robert.googlefirestroredemo.Manager;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.robert.googlefirestroredemo.Entity.User;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class FirebaseManager {

    private Firestore firestoreDB ;

    public FirebaseManager() {
        String pathToKey = "/Users/robertfranczak/Code/Java/GoogleFireStoreDemo/src/main/resources/security/key.json";
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

    public void addUser(User user) {
        if(!checkIfUsernameExists(user)) {
            DocumentReference docRef = firestoreDB.collection("users").document(user.username());

            Map<String, Object> userData = new HashMap<>();
            userData.put("firstname", user.firstname());
            userData.put("lastName", user.lastName());
            userData.put("email", user.email());
            userData.put("password", user.password());
            userData.put("rank", "user");

            docRef.set(userData);
        }
    }

    public boolean checkIfUsernameExists(User newUser)  {
        ApiFuture<QuerySnapshot> query = firestoreDB.collection("users").get();
        try {
            QuerySnapshot querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            Optional<QueryDocumentSnapshot> element = documents.stream()
                    .filter(user -> user.getId().equals(newUser.username())).findFirst();

            System.out.println(newUser.username() + " Exisit? - "+ element.isPresent());
            return element.isPresent();
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
