package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.entity.PlotEntity;
import com.galaxy.novelit.plot.repository.PlotRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class PlotServiceImpl implements PlotService{

    private final PlotRepository plotRepository;

    @Override
    public void createPlot(PlotCreateRequestDto dto) {
        // 작품, 플롯 uuid
        UUID plotUuid = UUID.randomUUID();

        PlotEntity plotEntity = PlotEntity.builder()
            .workspaceUuid(dto.getWorkspaceUuid())
            .plotUuid(plotUuid)
            .plotTitle(dto.getPlotTitle())
            .story(dto.getStory())
            .beginning(dto.getBeginning())
            .rising(dto.getRising())
            .crisis(dto.getCrisis())
            .climax(dto.getClimax())
            .ending(dto.getEnding())
            .build();

        plotRepository.save(plotEntity);
    }
}
