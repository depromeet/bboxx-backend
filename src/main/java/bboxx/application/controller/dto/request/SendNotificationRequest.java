package bboxx.application.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SendNotificationRequest {
    Long receiverId;
    Long emotionDiaryId;
}
