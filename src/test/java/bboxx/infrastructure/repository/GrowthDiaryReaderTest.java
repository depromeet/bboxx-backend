package bboxx.infrastructure.repository;

import bboxx.domain.emotion.GrowthDiary;
import bboxx.domain.emotion.query.GetAllGrowthDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.GrowthDiaryReader;
import bboxx.domain.emotion.querymodel.GrowthDiaryView;
import bboxx.infrastructure.repository.readmodel.GrowthDiaryReaderImpl;
import com.github.javafaker.Faker;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GrowthDiaryReader")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Slf4j
public class GrowthDiaryReaderTest {

    @Autowired
    private EntityManager entityManager;

    private final Faker faker = new Faker();

    @Nested
    class findAllInMonth {

        @Test
        void 정해진_달에_해당하는_growth_diary_를_반환한다() {
            // given
            JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
            GrowthDiaryReader growthDiaryReader = new GrowthDiaryReaderImpl(jpaQueryFactory);

            Long memberId = faker.number().randomNumber();

            LocalDateTime testDate = faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            int year = testDate.getYear();
            int month = testDate.getMonthValue();
            int inMonthSize = 5;
            int notInMonthSize = 4;

            for (int i = 0; i < inMonthSize; i++) {
                int day = faker.number().numberBetween(1, 27);
                int hour = faker.number().numberBetween(1, 23);
                int min = faker.number().numberBetween(1, 59);
                LocalDateTime date = LocalDateTime.of(year, month, day, hour, min);
                GrowthDiary growthDiary = new GrowthDiary("타이틀" + i, "컨텐츠" + i, memberId, faker.number().randomNumber(), new ArrayList<>(), date);
                entityManager.persist(growthDiary);
            }

            for (int i = 0; i < inMonthSize; i++) {
                int day = faker.number().numberBetween(1, 27);
                int hour = faker.number().numberBetween(1, 23);
                int min = faker.number().numberBetween(1, 59);
                LocalDateTime date = LocalDateTime.of(year, month, day, hour, min);
                GrowthDiary growthDiary = new GrowthDiary("타이틀" + i, "컨텐츠" + i, memberId, faker.number().randomNumber(), List.of("힘들다", "하하하"), date);
                entityManager.persist(growthDiary);
            }

            for (int i = 0; i < notInMonthSize; i++) {
                int fakeMonth = month > 1 ? month -1 : month + 1;
                int day = faker.number().numberBetween(1, 27);
                int hour = faker.number().numberBetween(1, 23);
                int min = faker.number().numberBetween(1, 59);
                LocalDateTime date = LocalDateTime.of(year, fakeMonth, day, hour, min);
                GrowthDiary growthDiary = new GrowthDiary("no타이틀" +i, "no컨텐츠" + i, memberId, faker.number().randomNumber(), new ArrayList<>(), date);
                entityManager.persist(growthDiary);
            }

            GetAllGrowthDiaryInMonthQuery query = new GetAllGrowthDiaryInMonthQuery(memberId, year, month);

            // when
            var result = growthDiaryReader.findAllInMonth(query);

            for (GrowthDiaryView view : result) {
                log.info("view: {}", view);
                log.info("tag: {}", view.getTags().size());
            }

            // then
            assertThat(result.size()).isEqualTo(inMonthSize + inMonthSize);
        }
    }
}
