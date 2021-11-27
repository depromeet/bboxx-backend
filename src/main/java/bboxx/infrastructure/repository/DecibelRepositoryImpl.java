package bboxx.infrastructure.repository;

import bboxx.domain.decibel.Decibel;
import bboxx.domain.decibel.commandmodel.DecibelRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class DecibelRepositoryImpl extends SimpleJpaRepository<Decibel, Long> implements DecibelRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public DecibelRepositoryImpl(EntityManager entityManager, JPAQueryFactory jpaQueryFactory) {
        super(Decibel.class, entityManager);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Decibel save(Decibel decibel) {
        return super.save(decibel);
    }
}
