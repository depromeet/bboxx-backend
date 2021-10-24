package bboxx.infrastructure.repository.readmodel;

import bboxx.domain.member.Member;
import bboxx.domain.member.querymodel.MemberReader;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static bboxx.domain.member.QMember.member;

@Repository
public class MemberReaderImpl implements MemberReader {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberReaderImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(member)
                .where(member.id.eq(id))
                .fetchOne()
        );
    }
}
