package bboxx.application.service.decibel;

import bboxx.domain.decibel.Decibel;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import bboxx.domain.decibel.command.CreateDecibelCommandResult;
import bboxx.domain.decibel.handler.CreateDecibelCommandHandler;
import bboxx.domain.decibel.query.GetDecibelQuery;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.domain.notification.handler.RegisterPushTokenCommandHandler;
import bboxx.infrastructure.repository.JpaDecibelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DecibelFacade {

    private final CreateDecibelCommandHandler createDecibelCommandHandler;
    private final JpaDecibelRepository decibelRepository;

    @Transactional
    public CreateDecibelCommandResult create(CreateDecibelCommand command) {
        return createDecibelCommandHandler.handle(command);
    }

    public GetDecibelQuery findDecibel(Long id) {
        Decibel decibel = decibelRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainErrorCode.DECIBEL_NOT_FOUND_ERROR));
        return new GetDecibelQuery(decibel.getDecibel());
    }
}
