package bboxx.domain.emotion.querymodel;

import bboxx.domain.emotion.query.GetAllGrowthDiaryInMonthQuery;

import java.util.List;

public interface GrowthDiaryReader {
    List<GrowthDiaryView> findAll();
    List<GrowthDiaryView> findAllInMonth(GetAllGrowthDiaryInMonthQuery query);
}
