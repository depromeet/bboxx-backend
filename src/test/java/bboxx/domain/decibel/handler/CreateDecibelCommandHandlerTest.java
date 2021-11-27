package bboxx.domain.decibel.handler;

import bboxx.domain.decibel.Decibel;
import bboxx.domain.decibel.FakeDecibelRepository;
import bboxx.domain.decibel.command.CreateDecibelCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CreateDecibelCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
class CreateDecibelCommandHandlerTest {

    @Test
    public void 새로운_데시벨을_생성한다() {
        // given
        FakeDecibelRepository decibelRepository = new FakeDecibelRepository();
        CreateDecibelCommand command = new CreateDecibelCommand(128L, 111L);

        CreateDecibelCommandHandler sut = new CreateDecibelCommandHandler(decibelRepository);

        // when
        Long decibelId = sut.handle(command).getId();

        // then
        Decibel actual = decibelRepository.findById(decibelId).get();

        assertThat(actual).isNotNull();
        assertThat(actual.getDecibel()).isEqualTo(128L);
        assertThat(actual.getMemberId()).isEqualTo(111L);
    }
}