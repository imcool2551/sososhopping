package com.sososhopping.server.common.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.admin.sdk.path}")
    private String firebaseSdkPath;

    private FirebaseApp firebaseApp;

    @PostConstruct
    public FirebaseApp init() {
        try {
            FileInputStream serviceAccount = new FileInputStream(firebaseSdkPath);
           FirebaseOptions.Builder builder = FirebaseOptions.builder();

            FirebaseOptions options = builder
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl("https://sososhop-d7574-default-rtdb.firebaseio.com")
                    .build();

            firebaseApp = FirebaseApp.initializeApp(options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return firebaseApp;
    }

    @Bean
    public FirebaseAuth firebaseAuth() {
        return FirebaseAuth.getInstance(firebaseApp);
    }
}
