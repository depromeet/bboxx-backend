package bboxx.domain.emotion.handler;

import bboxx.domain.emotion.Emotion;
import bboxx.domain.emotion.EmotionDiary;
import bboxx.domain.emotion.FakeEmotionDiaryRepository;
import bboxx.domain.emotion.FakeEmotionRepository;
import bboxx.domain.emotion.command.CreateEmotionDiaryCommand;
import bboxx.domain.exception.DomainErrorCode;
import bboxx.domain.exception.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CreateEmotionDiaryCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
class CreateEmotionDiaryCommandHandlerTest {

    @Test
    public void 새로운_emotionDiary_를_저장한다() {
        // given
        FakeEmotionRepository emotionRepository = new FakeEmotionRepository();
        FakeEmotionDiaryRepository emotionDiaryRepository = new FakeEmotionDiaryRepository();

        emotionRepository.emotions.addAll(List.of(
                new Emotion(1L, "하하", "111"),
                new Emotion(2L, "하하", "111"),
                new Emotion(3L, "하하", "111")
        ));

        CreateEmotionDiaryCommand command = new CreateEmotionDiaryCommand(
                "감정타이틀",
                "감정컨텐츠",
                123L,
                1L,
                List.of(1L, 2L, 3L)
        );

        CreateEmotionDiaryCommandHandler sut = new CreateEmotionDiaryCommandHandler(emotionRepository, emotionDiaryRepository);

        // when
        Long emotionDiaryId = sut.handle(command);

        // then
        Optional<EmotionDiary> actual = emotionDiaryRepository.findById(emotionDiaryId);
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getTitle()).isEqualTo("감정타이틀");
        assertThat(actual.get().getContent()).isEqualTo("감정컨텐츠");
        assertThat(actual.get().getMemberId()).isEqualTo(123L);
        assertThat(actual.get().getCategoryId()).isEqualTo(1L);
        assertThat(actual.get().getEmotionStatusList().size()).isEqualTo(3);
    }

    @Test
    public void 감정이_존재하지_않는다면_에러를_반환한다() {
        // given
        FakeEmotionRepository emotionRepository = new FakeEmotionRepository();
        FakeEmotionDiaryRepository emotionDiaryRepository = new FakeEmotionDiaryRepository();

        emotionRepository.emotions.addAll(List.of(
                new Emotion(1L, "하하", "111"),
                new Emotion(2L, "하하", "111")
        ));

        CreateEmotionDiaryCommand command = new CreateEmotionDiaryCommand(
                "감정타이틀",
                "감정컨텐츠",
                123L,
                1L,
                List.of(1L, 2L, 3L)
        );

        CreateEmotionDiaryCommandHandler sut = new CreateEmotionDiaryCommandHandler(emotionRepository, emotionDiaryRepository);

        // when
        // then
        DomainException actual = assertThrows(DomainException.class, () -> {
            sut.handle(command);
        });
        assertThat(actual).isEqualTo(new DomainException(DomainErrorCode.EMOTION_NOT_FOUND_ERROR));
    }
}