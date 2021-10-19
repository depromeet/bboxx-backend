package bboxx.infrastructure.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class FirebaseInitializer {

//    private static final String FIREBASE_CLASS_PATH = "firebase/serviceAccountKey.json";
    @Value("${bboxx.firebase.project-id}")
    String projectId;

    @Value("${bboxx.firebase.client-id}")
    String clientId;

    @Value("${bboxx.firebase.client-email}")
    String clientEmail;

    @Value("${bboxx.firebase.private-key}")
    String privateKey;

    @Value("${bboxx.firebase.private-key-id}")
    String privateKeyId;

    @PostConstruct
    public void initialize() {
        try {
            GoogleCredentials credentials = ServiceAccountCredentials
                    .fromPkcs8(
                            clientId,
                            clientEmail,
                            privateKey,
                            privateKeyId,
                            null
                    );

            FirebaseOptions options = FirebaseOptions.builder()
                    .setProjectId(projectId)
                    .setCredentials(credentials)
//                    .setCredentials(GoogleCredentials
//                            .fromStream(new ClassPathResource(FIREBASE_CLASS_PATH).getInputStream()))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
