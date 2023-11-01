package com.galaxy.novelit.plot.repository;

import com.galaxy.novelit.plot.entity.PlotEntity;
import com.galaxy.novelit.plot.entity.QPlotEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PlotRepositoryImpl implements PlotRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<PlotEntity>> findByKeyword(String workspaceUuid, String keyword) {

        String pattern = keyword + "%";
        String pattern2 = "%" + keyword + "%";

        QPlotEntity qPlot = QPlotEntity.plotEntity;

        List<PlotEntity> list1 = queryFactory
            .selectFrom(qPlot)
            .where(
                qPlot.plotTitle.eq(keyword)
                    .and(qPlot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        List<PlotEntity> list2 = queryFactory
            .selectFrom(qPlot)
            .where(
                qPlot.plotTitle.like(pattern)
                    .and(qPlot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        List<PlotEntity> list3 = queryFactory
            .selectFrom(qPlot)
            .where(
                qPlot.plotTitle.like(pattern2)
                    .and(qPlot.workspaceUuid.eq(workspaceUuid))
            )
            .fetch();

        LinkedHashMap<String, PlotEntity> plotEntityHashMap = new LinkedHashMap<>();

        List<PlotEntity> result = new ArrayList<>();
        result.addAll(list1);
        result.addAll(list2);
        result.addAll(list3);

        for (PlotEntity plotEntity : result) {
            plotEntityHashMap.put(plotEntity.getPlotUuid(), plotEntity);
        }

        List<PlotEntity> valueList = new ArrayList<>(plotEntityHashMap.values());


        return Optional.ofNullable(valueList);
    }
}
