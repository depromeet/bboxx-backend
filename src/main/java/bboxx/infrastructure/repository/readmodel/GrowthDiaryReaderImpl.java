package bboxx.infrastructure.repository.readmodel;

import bboxx.domain.emotion.GrowthDiaryTag;
import bboxx.domain.emotion.query.GetAllGrowthDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.GrowthDiaryReader;
import bboxx.domain.emotion.querymodel.GrowthDiaryView;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static bboxx.domain.emotion.QGrowthDiary.growthDiary;
import static bboxx.domain.emotion.QGrowthDiaryTag.growthDiaryTag;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Repository
@Slf4j
public class GrowthDiaryReaderImpl implements GrowthDiaryReader {

    private final JPAQueryFactory jpaQueryFactory;

    public GrowthDiaryReaderImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<GrowthDiaryView> findAll() {
        return jpaQueryFactory
                .select(Projections.fields(GrowthDiaryView.class,
                        growthDiary.id,
                        growthDiary.memberId,
                        growthDiary.title,
                        growthDiary.content,
                        growthDiary.keptAt,
                        growthDiary.createdAt,
                        growthDiary.updatedAt,
                        growthDiary.emotionDiaryId
                )).from(growthDiary)
                .fetch();
    }

    @Override
    public List<GrowthDiaryView> findAllInMonth(GetAllGrowthDiaryInMonthQuery query) {
        return jpaQueryFactory
                .from(growthDiary)
                .leftJoin(growthDiaryTag).on(growthDiary.id.eq(growthDiaryTag.growthDiaryId))
                .where(
                        growthDiary.memberId.eq(query.getMemberId()),
                        inYearMonth(query.getYear(), query.getMonth())
                )
                .transform(
                        groupBy(growthDiary.id).list(
                                Projections.fields(GrowthDiaryView.class,
                                        growthDiary.id,
                                        growthDiary.memberId,
                                        growthDiary.title,
                                        growthDiary.content,
                                        growthDiary.keptAt,
                                        growthDiary.createdAt,
                                        growthDiary.updatedAt,
                                        growthDiary.emotionDiaryId,
                                        list(growthDiaryTag.tag).as("tags")
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
        return growthDiary.keptAt.between(from, to);
    }
}
