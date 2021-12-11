package bboxx.domain.emotion.commandmodel;

import bboxx.domain.emotion.EmotionDiary;

import java.util.Optional;

public interface EmotionDiaryRepository {
    EmotionDiary save(EmotionDiary emotionDiary);
    Optional<EmotionDiary> findById(Long id);
    void deleteById(Long id);
}
