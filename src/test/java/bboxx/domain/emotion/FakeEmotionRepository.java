package bboxx.domain.emotion;

import bboxx.domain.emotion.commandmodel.EmotionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeEmotionRepository implements EmotionRepository {

    public List<Emotion> emotions = new ArrayList<>();

    @Override
    public Optional<Emotion> findById(Long id) {
        return emotions.stream()
                .filter(emotion -> id.equals(emotion.getId()))
                .findFirst();
    }

    @Override
    public List<Emotion> findAll() {
        return emotions;
    }
}
