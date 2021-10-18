package bboxx.application.service.decibel;

import bboxx.domain.decibel.Decibel;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import bboxx.domain.decibel.command.CreateDecibelCommandResult;
import bboxx.domain.decibel.command.FindDecibelCommandResult;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.infrastructure.repository.JpaDecibelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DecibelService {

    private final JpaDecibelRepository decibelRepository;
    @Transactional
    public CreateDecibelCommandResult create(CreateDecibelCommand command) {
        return new CreateDecibelCommandResult(decibelRepository.save(command.toEntity()).getId());
    }

}
