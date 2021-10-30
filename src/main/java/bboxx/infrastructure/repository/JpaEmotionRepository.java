package bboxx.infrastructure.repository;

import bboxx.domain.emotion.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmotionRepository extends JpaRepository<Emotion, Long> {
}
