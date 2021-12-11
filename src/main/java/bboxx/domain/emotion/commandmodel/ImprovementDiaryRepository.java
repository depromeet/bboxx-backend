package bboxx.domain.emotion.commandmodel;

import bboxx.domain.emotion.ImprovementDiary;

import java.util.Optional;

public interface ImprovementDiaryRepository {
    ImprovementDiary save(ImprovementDiary improvementDiary);
    Optional<ImprovementDiary> findById(Long id);
}
