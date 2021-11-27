package bboxx.application.config;

import bboxx.domain.decibel.commandmodel.DecibelRepository;
import bboxx.domain.decibel.handler.CreateDecibelCommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class DecibelConfiguration {

    private final DecibelRepository decibelRepository;

    @Bean
    public CreateDecibelCommandHandler createDecibelCommandHandler() {
        return new CreateDecibelCommandHandler(decibelRepository);
    }
}
