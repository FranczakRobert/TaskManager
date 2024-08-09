package com.robert.googlefirestroredemo.Controller;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
public class TestController {
    Firestore db;

    private TestController() throws IOException {

        String pathToKey = "/Users/robertfranczak/Code/Java/GoogleFireStroreDemo/src/main/resources/security/key.json";
        FileInputStream serviceAccount = new FileInputStream(pathToKey);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
    }

    @GetMapping("/saveTest")
    public String index() {

        DocumentReference docRef = db.collection("users").document("alovelace");

        Map<String, Object> data = new HashMap<>();
        data.put("first", "Robert");
        data.put("last", "Franczak");
        data.put("born", 1998);

        docRef.set(data);

        data = new HashMap<>();
        data.put("first", "Michael");
        data.put("last", "David");
        data.put("born", 1999);

        DocumentReference docRef1 = db.collection("users").document("user2");
        docRef1.set(data);

        return "index";
    }

    @GetMapping("/getTest")
    public String getTest() throws ExecutionException, InterruptedException {

        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection("users").get();

        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            System.out.println("User: " + document.getId());
            System.out.println("First: " + document.getString("first"));
            if (document.contains("middle")) {
                System.out.println("Middle: " + document.getString("middle"));
            }
            System.out.println("Last: " + document.getString("last"));
            System.out.println("Born: " + document.getLong("born"));
        }

        return "blah";
    }
}
