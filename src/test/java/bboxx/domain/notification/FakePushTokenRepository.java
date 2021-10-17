package bboxx.domain.notification;

import bboxx.domain.helper.RandomIdGenerator;
import bboxx.domain.notification.commandmodel.PushTokenRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakePushTokenRepository implements PushTokenRepository {

    public List<PushToken> pushTokens = new ArrayList<>();

    @Override
    public PushToken save(PushToken pushToken) {
        if (pushToken.getId() == null) {
            PushToken newPushToken = new PushToken(
                    RandomIdGenerator.generate(pushTokens.stream().map(PushToken::getId).collect(Collectors.toList())),
                    pushToken.getOwnerId(),
                    pushToken.getToken(),
                    pushToken.getState()
            );
            this.pushTokens.add(newPushToken);
            return newPushToken;
        } else {
            int memberIndex = pushTokens.stream()
                    .map(PushToken::getId)
                    .collect(Collectors.toList())
                    .indexOf(pushToken.getId());
            if (memberIndex == -1) {
                pushTokens.add(pushToken);
            } else {
                pushTokens.set(memberIndex, pushToken);
            }
            return pushToken;
        }
    }

    @Override
    public Optional<PushToken> findById(Long id) {
        return pushTokens.stream()
                .filter(pushToken -> id.equals(pushToken.getId()))
                .findFirst();
    }

    @Override
    public Optional<PushToken> findByOwnerId(Long ownerId) {
        return pushTokens.stream()
                .filter(pushToken -> ownerId.equals(pushToken.getOwnerId()))
                .findFirst();
    }
}

