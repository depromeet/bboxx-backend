package bboxx.infrastructure.repository;

import bboxx.domain.emotion.ImprovementDiary;
import bboxx.domain.emotion.commandmodel.ImprovementDiaryRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class ImprovementDiaryRepositoryImpl extends SimpleJpaRepository<ImprovementDiary, Long> implements ImprovementDiaryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ImprovementDiaryRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(ImprovementDiary.class, entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public ImprovementDiary save(ImprovementDiary improvementDiary) {
        return super.save(improvementDiary);
    }
}
