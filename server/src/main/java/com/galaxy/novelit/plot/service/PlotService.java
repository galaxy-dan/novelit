package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotListRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import java.util.Optional;

public interface PlotService {

    //PlotListResponseDto getPlotList(PlotListRequestDto plotListRequestDto);
    PlotListResponseDto getPlotList(String workspaceUuid);
    PlotListResponseDto getPlotListByKeyword(String workspaceUuid, String keyword);
    void createPlot(PlotCreateRequestDto plotCreateRequestDto);
    PlotDetailsResponseDto getPlotDetails(String plotUUID);
    void savePlot(PlotSaveRequestDto plotSaveRequestDto);
    void deletePlot(String plotUuid);
}
