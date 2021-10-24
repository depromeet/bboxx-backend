package bboxx.application.service.emotion;

import bboxx.domain.emotion.query.GetAllGrowthDiaryInMonthQuery;
import bboxx.domain.emotion.querymodel.GrowthDiaryReader;
import bboxx.domain.emotion.querymodel.GrowthDiaryView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllGrowthDiaryInMonthQueryHandler {

    private final GrowthDiaryReader growthDiaryReader;

    public GetAllGrowthDiaryInMonthQueryHandler(GrowthDiaryReader growthDiaryReader) {
        this.growthDiaryReader = growthDiaryReader;
    }

    public List<GrowthDiaryView> handle(GetAllGrowthDiaryInMonthQuery query) {
        return growthDiaryReader.findAllInMonth(query);
    }
}
