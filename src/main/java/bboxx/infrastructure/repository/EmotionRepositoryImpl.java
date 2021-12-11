package bboxx.infrastructure.repository;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.commandmodel.EmotionRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class EmotionRepositoryImpl extends SimpleJpaRepository<Emotion, Long> implements EmotionRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public EmotionRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(Emotion.class, entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }
}
