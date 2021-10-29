package bboxx.infrastructure.repository;

import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.notification.Notification;
import bboxx.domain.notification.NotificationState;
import bboxx.domain.notification.query.GetAllNotificationQuery;
import bboxx.domain.notification.querymodel.NotificationReader;
import bboxx.infrastructure.repository.readmodel.NotificationReaderImpl;
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

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("NotificationReader")
@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Slf4j
public class NotificationReaderTest {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private JpaEmotionDiaryRepository emotionDiaryRepository;

    @Nested
    class findByReceiverId {

        @Test
        void receiver_id_와_일치하는_갯수를_반환한다() {
            // given
            JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
            NotificationReader notificationReader = new NotificationReaderImpl(jpaQueryFactory);

            Long receiverId = 123L;


            Notification notification = new Notification(receiverId, 123L, "타이틀입이다.", "메시지입니다", NotificationState.SENT);
            entityManager.persist(notification);

            GetAllNotificationQuery query = new GetAllNotificationQuery(receiverId, null, new ArrayList<>(), 30L);

            // when
            var result = notificationReader.findAllByQuery(query);

            // then
            assertThat(result.size()).isGreaterThanOrEqualTo(1);
            assertThat(result.get(0)).isNotNull();
            assertThat(result.get(0).getReceiverId()).isEqualTo(receiverId);
        }
    }
}
