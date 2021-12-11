package bboxx.infrastructure.repository;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.commandmodel.EmotionDiaryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class EmotionDiaryRepositoryImpl extends SimpleJpaRepository<EmotionDiary, Long> implements EmotionDiaryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public EmotionDiaryRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(EmotionDiary.class, entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public EmotionDiary save(EmotionDiary emotionDiary) {
        return super.save(emotionDiary);
    }
}
