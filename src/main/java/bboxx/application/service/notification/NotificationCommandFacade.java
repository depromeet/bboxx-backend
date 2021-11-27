package bboxx.application.service.notification;

import bboxx.application.controller.dto.request.DeregisterPushTokenRequest;
import bboxx.application.controller.dto.request.RegisterPushTokenRequest;
import bboxx.application.controller.dto.request.SendNotificationRequest;
import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.member.Member;
import bboxx.domain.member.commandmodel.MemberRepository;
import bboxx.domain.notification.Notification;
import bboxx.domain.notification.PushToken;
import bboxx.domain.notification.command.DeregisterPushTokenCommand;
import bboxx.domain.notification.command.RegisterPushTokenCommand;
import bboxx.domain.notification.command.SendNotificationCommand;
import bboxx.domain.notification.handler.DeregisterPushTokenCommandHandler;
import bboxx.domain.notification.handler.RegisterPushTokenCommandHandler;
import bboxx.domain.notification.handler.SendNotificationCommandHandler;
import bboxx.infrastructure.repository.JpaEmotionDiaryRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class NotificationCommandFacade {

    private final RegisterPushTokenCommandHandler registerPushTokenCommandHandler;
    private final DeregisterPushTokenCommandHandler deregisterPushTokenCommandHandler;
    private final SendNotificationCommandHandler sendNotificationCommandHandler;

    private final MemberRepository memberRepository;
    private final JpaEmotionDiaryRepository emotionDiaryRepository;

    @Transactional
    public PushToken registerPushToken(RegisterPushTokenRequest request) {

        Member member = memberRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        return registerPushTokenCommandHandler.handle(new RegisterPushTokenCommand(
                request.getOwnerId(),
                member.getNickname(),
                request.getToken()
        ));
    }

    @Transactional
    public PushToken deregisterPushToken(DeregisterPushTokenRequest request) {

        Member member = memberRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.MEMBER_NOT_FOUND_ERROR));

        return deregisterPushTokenCommandHandler.handle(new DeregisterPushTokenCommand(
                request.getOwnerId(),
                member.getNickname()
        ));
    }

    @Transactional
    public Notification sendNotification(SendNotificationRequest request) {

        EmotionDiary emotionDiary = emotionDiaryRepository.findById(request.getEmotionDiaryId())
                .orElseThrow(() -> new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));
        emotionDiary.sendNotification();

        return sendNotificationCommandHandler.handle(new SendNotificationCommand(
                request.getReceiverId(),
                request.getEmotionDiaryId(),
                emotionDiary.getTitle(),
                emotionDiary.getCreatedAt()
        ));
    }
}
