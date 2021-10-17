package bboxx.application.service.notification;

import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.query.GetPushTokenOneQuery;
import bboxx.domain.notification.querymodel.PushTokenReader;
import bboxx.domain.notification.querymodel.PushTokenView;
import org.springframework.stereotype.Service;

@Service
public class GetPushTokenOneQueryHandler {

    private final PushTokenReader pushTokenReader;

    public GetPushTokenOneQueryHandler(PushTokenReader pushTokenReader) {
        this.pushTokenReader = pushTokenReader;
    }

    public PushTokenView handle(GetPushTokenOneQuery query) {
        return pushTokenReader.findByOwnerId(query.getOwnerId())
                .map(PushTokenView::of)
                .orElseThrow(() -> new DomainException(DomainErrorCode.PUSH_TOKEN_NOT_FOUND_ERROR));
    }
}
