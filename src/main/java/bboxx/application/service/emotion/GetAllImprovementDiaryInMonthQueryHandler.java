package bboxx.application.service.emotion;

import bboxx.domain.emotion.query.GetAllImprovementDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.ImprovementDiaryReader;
import bboxx.domain.emotion.querymodel.ImprovementDiaryView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllImprovementDiaryInMonthQueryHandler {

    private final ImprovementDiaryReader improvementDiaryReader;

    public GetAllImprovementDiaryInMonthQueryHandler(ImprovementDiaryReader improvementDiaryReader) {
        this.improvementDiaryReader = improvementDiaryReader;
    }

    public List<ImprovementDiaryView> handle(GetAllImprovementDiaryInMonthQuery query) {
        return improvementDiaryReader.findAllInMonth(query);
    }
}
