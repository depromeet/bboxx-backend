package bboxx.infrastructure.repository.readmodel;

import bboxx.domain.notification.NotificationState;
import bboxx.domain.notification.query.GetAllNotificationQuery;
import bboxx.domain.notification.querymodel.NotificationReader;
import bboxx.domain.notification.querymodel.NotificationView;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static bboxx.domain.emotion.QEmotionDiary.emotionDiary;
import static bboxx.domain.notification.QNotification.notification;

@Repository
public class NotificationReaderImpl implements NotificationReader {

    private final JPAQueryFactory jpaQueryFactory;

    public NotificationReaderImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<NotificationView> findAllByQuery(GetAllNotificationQuery query) {
        return jpaQueryFactory
                .select(Projections.fields(NotificationView.class,
                        notification.id,
                        notification.receiverId,
                        notification.title,
                        notification.message,
                        notification.createdAt,
                        notification.updatedAt,
                        notification.state,
                        emotionDiary
                )).from(notification)
                .join(emotionDiary).on(notification.emotionDiaryId.eq(emotionDiary.id))
                .where(
                        notification.receiverId.eq(query.getReceiverId()),
                        lessThanCursorId(query.getCursorId()),
                        inStates(query.getStates())
                )
                .limit(query.getLimit())
                .fetch();
    }

    private BooleanExpression inStates(List<NotificationState> states) {
        if (states.size() < 1) {
            return null;
        }
        return notification.state.in(states);
    }

    private BooleanExpression lessThanCursorId(Long cursorId) {
        if (cursorId == null) {
            return null;
        }
        return notification.id.lt(cursorId);
    }
}
