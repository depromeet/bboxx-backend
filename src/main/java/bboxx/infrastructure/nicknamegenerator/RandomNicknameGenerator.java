package bboxx.infrastructure.nicknamegenerator;

import bboxx.domain.member.commandmodel.NicknameGenerator;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class RandomNicknameGenerator implements NicknameGenerator {

    private static final String[] randomNickNameModifier = {
            "귀여운", "새침한", "발랄한", "멋쟁이", "열불난"
    };
    private static final String[] randomNickNameSubject = {
            "사과", "고양이", "춘식이", "라이언", "펭귄", "도토리"
    };

    @Override
    public String generate() {
        return generateRandomModifier() +
                generateRandomSubject() +
                new Random().nextInt(10);
    }

    private String generateRandomModifier() {
        Random rand = new Random();
        return randomNickNameModifier[rand.nextInt(randomNickNameModifier.length)];
    }

    private String generateRandomSubject() {
        Random rand = new Random();
        return randomNickNameSubject[rand.nextInt(randomNickNameSubject.length)];
    }
}
