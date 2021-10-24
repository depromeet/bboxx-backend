package bboxx.domain.emotion.querymodel;

import bboxx.domain.emotion.query.GetAllImprovementDiaryInMonthQuery;

import java.util.List;

public interface ImprovementDiaryReader {
    List<ImprovementDiaryView> findAll();
    List<ImprovementDiaryView> findAllInMonth(GetAllImprovementDiaryInMonthQuery query);
}
