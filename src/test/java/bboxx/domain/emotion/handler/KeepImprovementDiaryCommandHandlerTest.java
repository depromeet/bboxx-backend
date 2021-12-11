package bboxx.domain.emotion.handler;

import bboxx.domain.emotion.*;
import bboxx.domain.emotion.command.KeepImprovementDiaryCommand;
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

@DisplayName("KeepImprovementDiaryCommandHandlerTest")
@ExtendWith(MockitoExtension.class)
class KeepImprovementDiaryCommandHandlerTest {

    @Test
    public void emotionDiary가_존재하지_않는다면_에러를_반환한다() {
        // given
        FakeEmotionDiaryRepository emotionDiaryRepository = new FakeEmotionDiaryRepository();
        FakeImprovementDiaryRepository improvementDiaryRepository = new FakeImprovementDiaryRepository();

        KeepImprovementDiaryCommand command = new KeepImprovementDiaryCommand(
                1L,
                "타이틀입니다.",
                "컨텐츠입니다.",
                123L,
                List.of("힘들어", "쉬고싶어", "껄껄")
        );

        KeepImprovementDiaryCommandHandler sut = new KeepImprovementDiaryCommandHandler(emotionDiaryRepository, improvementDiaryRepository);

        // when
        // then
        DomainException actual = assertThrows(DomainException.class, () -> {
            sut.handle(command);
        });
        assertThat(actual).isEqualTo(new DomainException(DomainErrorCode.EMOTION_DIARY_NOT_FOUND_ERROR));
    }

    @Test
    public void 새로운_improvementDiary_를_저장한다() {
        // given
        FakeEmotionDiaryRepository emotionDiaryRepository = new FakeEmotionDiaryRepository();
        FakeImprovementDiaryRepository improvementDiaryRepository = new FakeImprovementDiaryRepository();

        EmotionDiary emotionDiary = new EmotionDiary(
                "우와아",
                "컨텐츠컨텐츠",
                123L,
                1L,
                List.of(
                        new Emotion(1L, "하하", "123"),
                        new Emotion(2L, "히히", "1234")
                )
        );
        emotionDiaryRepository.save(emotionDiary);

        KeepImprovementDiaryCommand command = new KeepImprovementDiaryCommand(
                emotionDiary.getId(),
                "타이틀입니다.",
                "컨텐츠입니다.",
                123L,
                List.of("힘들어", "쉬고싶어", "껄껄")
        );

        KeepImprovementDiaryCommandHandler sut = new KeepImprovementDiaryCommandHandler(emotionDiaryRepository, improvementDiaryRepository);

        // when
        Long improvementDiaryId = sut.handle(command);

        // then
        Optional<ImprovementDiary> actual = improvementDiaryRepository.findById(improvementDiaryId);
        assertThat(actual.isPresent()).isTrue();
        assertThat(actual.get().getTitle()).isEqualTo("타이틀입니다.");
        assertThat(actual.get().getContent()).isEqualTo("컨텐츠입니다.");
        assertThat(actual.get().getMemberId()).isEqualTo(123L);
        assertThat(actual.get().getTags().size()).isEqualTo(3);
    }
}