package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.common.exception.custom.CustomException;
import com.galaxy.novelit.common.exception.custom.ErrorCode;
import com.galaxy.novelit.plot.request.PlotCreateRequest;
import com.galaxy.novelit.plot.request.PlotUpdateRequest;
import com.galaxy.novelit.plot.response.PlotDetailResponse;
import com.galaxy.novelit.plot.response.PlotListResponse;
import com.galaxy.novelit.plot.entity.Plot;
import com.galaxy.novelit.plot.repository.PlotRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class PlotService {

    private final PlotRepository plotRepository;

    @Transactional(readOnly = true)
    public PlotListResponse getPlotListByKeyword(String workspaceUuid, String keyword) {
        List<Plot> plots = plotRepository.findByKeyword(workspaceUuid, keyword)
                .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "가져올 플롯 목록이 없습니다."));

        return PlotListResponse.plotListResponse(plots);
    }


    @Transactional(readOnly = true)
    public PlotListResponse getPlotList(String workspaceUuid) {
        List<Plot> plots = plotRepository.findAllByWorkspaceUuid(workspaceUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "가져올 플롯 목록이 없습니다."));
        return PlotListResponse.plotListResponse(plots);
    }


    @Transactional
    public void createPlot(PlotCreateRequest plotCreateRequest) {
        plotRepository.save(Plot.create(plotCreateRequest));
    }

    @Transactional(readOnly = true)
    public PlotDetailResponse getPlotDetails(String plotUuid) {
        Plot plot = plotRepository.findPlotByPlotUuid(plotUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "세부정보를 가져올 플롯이 없습니다."));

        return new PlotDetailResponse(plot);
    }

    @Transactional
    public void updatePlot(PlotUpdateRequest plotUpdateRequest) {
        Plot plot = plotRepository.findPlotByPlotUuid(plotUpdateRequest.plotUuid())
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "업데이트 할 플롯이 없습니다."));

        plot.updatePlot(plotUpdateRequest);
    }


    @Transactional
    public void deletePlot(String plotUuid) {
        plotRepository.deletePlotByPlotUuid(plotUuid)
            .orElseThrow(() -> new CustomException(ErrorCode.NO_SUCH_PLOT, "삭제할 플롯이 없습니다."));
    }
}
