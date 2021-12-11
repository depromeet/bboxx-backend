package bboxx.domain.emotion;

import bboxx.domain.emotion.commandmodel.ImprovementDiaryRepository;
import bboxx.domain.helper.RandomIdGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeImprovementDiaryRepository implements ImprovementDiaryRepository {

    public List<ImprovementDiary> improvementDiaries = new ArrayList<>();

    @Override
    public ImprovementDiary save(ImprovementDiary improvementDiary) {
        if (improvementDiary.getId() == null) {
            try {
                Field idField = improvementDiary.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(improvementDiary, RandomIdGenerator.generate(improvementDiaries.stream().map(ImprovementDiary::getId).collect(Collectors.toList())));
            } catch (Exception e) {
                // do nothing ...
            }
            this.improvementDiaries.add(improvementDiary);
        } else {
            int memberIndex = improvementDiaries.stream()
                    .map(ImprovementDiary::getId)
                    .collect(Collectors.toList())
                    .indexOf(improvementDiary.getId());
            if (memberIndex == -1) {
                improvementDiaries.add(improvementDiary);
            } else {
                improvementDiaries.set(memberIndex, improvementDiary);
            }
        }
        return improvementDiary;
    }

    @Override
    public Optional<ImprovementDiary> findById(Long id) {
        return improvementDiaries.stream()
                .filter(diary -> id.equals(diary.getId()))
                .findFirst();
    }
}
