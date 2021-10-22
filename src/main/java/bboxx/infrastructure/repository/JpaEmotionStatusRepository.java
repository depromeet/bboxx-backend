package bboxx.infrastructure.repository;

import bboxx.domain.emotion.EmotionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEmotionStatusRepository extends JpaRepository<EmotionStatus, Long> {
}
