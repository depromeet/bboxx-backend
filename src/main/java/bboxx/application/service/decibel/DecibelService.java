package bboxx.application.service.decibel;

import bboxx.domain.decibel.Decibel;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import bboxx.domain.decibel.command.CreateDecibelCommandResult;
import bboxx.domain.decibel.query.GetDecibelCommand;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import bboxx.infrastructure.repository.JpaDecibelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class DecibelService {

    private final JpaDecibelRepository decibelRepository;
    @Transactional
    public CreateDecibelCommandResult create(CreateDecibelCommand command) {
        return new CreateDecibelCommandResult(decibelRepository.save(command.toEntity()).getId());
    }

    public GetDecibelCommand findDecibel(Long id) {
        Decibel decibel = decibelRepository.findById(id)
                .orElseThrow(() -> new DomainException(DomainErrorCode.DECIBEL_NOT_FOUND_ERROR));
        return new GetDecibelCommand(decibel.getDecibel());
    }
}
