package bboxx.infrastructure.repository;

import bboxx.domain.emotion.EmotionDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmotionRepository extends JpaRepository<EmotionDiary, Long> {
}
