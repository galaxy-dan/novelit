package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import java.util.Optional;

public interface PlotService {
    void createPlot(PlotCreateRequestDto plotCreateRequestDto);
    PlotDetailsResponseDto getPlotDetails(String plotUUID);

    void savePlot(PlotSaveRequestDto plotSaveRequestDto);
}
