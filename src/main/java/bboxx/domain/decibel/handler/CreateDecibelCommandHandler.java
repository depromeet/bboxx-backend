package bboxx.domain.decibel.handler;

import bboxx.domain.CommandHandler;
import bboxx.domain.decibel.Decibel;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import bboxx.domain.decibel.command.CreateDecibelCommandResult;
import bboxx.domain.decibel.commandmodel.DecibelRepository;

public class CreateDecibelCommandHandler implements CommandHandler<CreateDecibelCommand, CreateDecibelCommandResult> {

    private final DecibelRepository decibelRepository;

    public CreateDecibelCommandHandler(DecibelRepository decibelRepository) {
        this.decibelRepository = decibelRepository;
    }

    @Override
    public CreateDecibelCommandResult handle(CreateDecibelCommand command) {

        Decibel decibel = Decibel.builder()
                .decibel(command.getDecibel())
                .memberId(command.getMemberId())
                .build();

        decibelRepository.save(decibel);
        return new CreateDecibelCommandResult(decibel.getId());
    }
}
