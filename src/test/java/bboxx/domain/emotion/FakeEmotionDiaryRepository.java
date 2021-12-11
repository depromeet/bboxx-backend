package bboxx.domain.emotion;

import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;
import bboxx.domain.helper.RandomIdGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeEmotionDiaryRepository implements EmotionDiaryRepository {

    public List<EmotionDiary> emotionDiaries = new ArrayList<>();

    @Override
    public EmotionDiary save(EmotionDiary emotionDiary) {
        if (emotionDiary.getId() == null) {
            try {
                Field idField = emotionDiary.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(emotionDiary, RandomIdGenerator.generate(emotionDiaries.stream().map(EmotionDiary::getId).collect(Collectors.toList())));
            } catch (Exception e) {
                // do nothing ...
            }
            this.emotionDiaries.add(emotionDiary);
        } else {
            int index = emotionDiaries.stream()
                    .map(EmotionDiary::getId)
                    .collect(Collectors.toList())
                    .indexOf(emotionDiary.getId());
            if (index == -1) {
                emotionDiaries.add(emotionDiary);
            } else {
                emotionDiaries.set(index, emotionDiary);
            }
        }
        return emotionDiary;
    }

    @Override
    public Optional<EmotionDiary> findById(Long id) {
        return emotionDiaries.stream()
                .filter(emotionDiary -> id.equals(emotionDiary.getId()))
                .findFirst();
    }

    @Override
    public void deleteById(Long id) {
        emotionDiaries = emotionDiaries.stream()
                .filter(emotionDiary -> !id.equals(emotionDiary.getId()))
                .collect(Collectors.toList());
    }
}
