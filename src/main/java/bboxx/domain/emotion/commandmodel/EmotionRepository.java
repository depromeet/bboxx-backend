package bboxx.domain.emotion.commandmodel;

import bboxx.domain.emotion.Emotion;

import java.util.List;
import java.util.Optional;

public interface EmotionRepository {
    Optional<Emotion> findById(Long id);
    List<Emotion> findAll();
}
