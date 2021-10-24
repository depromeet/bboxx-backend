package bboxx.domain.emotion.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class GetAllImprovementDiaryInMonthQuery {
    Long memberId;
    int year;
    int month;
}
