package bboxx.infrastructure.repository.readmodel;

import bboxx.domain.emotion.query.GetAllImprovementDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.ImprovementDiaryReader;
import bboxx.domain.emotion.querymodel.ImprovementDiaryView;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static bboxx.domain.emotion.QImprovementDiary.improvementDiary;
import static bboxx.domain.emotion.QImprovementDiaryTag.improvementDiaryTag;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@Slf4j
public class ImprovementDiaryReaderImpl implements ImprovementDiaryReader {

    private final JPAQueryFactory jpaQueryFactory;

    public ImprovementDiaryReaderImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ImprovementDiaryView> findAll() {
        return jpaQueryFactory
                .select(Projections.fields(ImprovementDiaryView.class,
                        improvementDiary.id,
                        improvementDiary.memberId,
                        improvementDiary.title,
                        improvementDiary.content,
                        improvementDiary.keptAt,
                        improvementDiary.createdAt,
                        improvementDiary.updatedAt,
                        improvementDiary.emotionDiaryId
                )).from(improvementDiary)
                .fetch();
    }

    @Override
    public List<ImprovementDiaryView> findAllInMonth(GetAllImprovementDiaryInMonthQuery query) {
        return jpaQueryFactory
                .from(improvementDiary)
                .leftJoin(improvementDiaryTag).on(improvementDiary.id.eq(improvementDiaryTag.improvementDiaryId))
                .where(
                        improvementDiary.memberId.eq(query.getMemberId()),
                        inYearMonth(query.getYear(), query.getMonth())
                )
                .transform(
                        groupBy(improvementDiary.id).list(
                                Projections.fields(ImprovementDiaryView.class,
                                        improvementDiary.id,
                                        improvementDiary.memberId,
                                        improvementDiary.title,
                                        improvementDiary.content,
                                        improvementDiary.keptAt,
                                        improvementDiary.createdAt,
                                        improvementDiary.updatedAt,
                                        improvementDiary.emotionDiaryId,
                                        list(improvementDiaryTag.tag).as("tags")
                                )
                        )
                );
    }

    private BooleanExpression inYearMonth(int year, int month) {
        if (year < 1970) {
            year = LocalDate.now().getYear();
        }

        if (month < 0 || month > 12) {
            month = LocalDate.now().getMonthValue();
        }

        LocalDate initial = LocalDate.of(year, month, 15);
        LocalDateTime from = initial.withDayOfMonth(1).atStartOfDay();
        LocalDateTime to = initial.plusMonths(1).withDayOfMonth(1).atStartOfDay().minusSeconds(1);
        return improvementDiary.keptAt.between(from, to);
    }
}
