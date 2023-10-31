package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.common.exception.NoSuchPlotException;
import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.entity.PlotEntity;
import com.galaxy.novelit.plot.repository.PlotRepository;
import java.util.Optional;
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

        String plotString = plotUuid.toString();
        
        /*PlotEntity plotEntity = PlotEntity.builder()
            .workspaceUuid(workspaceUuid)
            .plotUuid(plotUuid)
            .plotTitle(plotTitle)
            .story(story)
            .beginning(beginning)
            .rising(rising)
            .crisis(crisis)
            .climax(climax)
            .ending(ending)
            .build();*/

        plotRepository.save(PlotEntity.create(plotString, dto));
    }

    @Override
    public PlotDetailsResponseDto getPlotDetails(String plotUuid) {

        // entity 찾음
        PlotEntity plotEntity = plotRepository.findPlotEntityByPlotUuid(plotUuid)
            .orElseThrow(() -> new NoSuchPlotException());

        // entity -> dto
        return PlotDetailsResponseDto.toDto(plotEntity);
    }

    @Override
    public void savePlot(PlotSaveRequestDto dto) {
        // 플롯 Uuid로 찾기
        PlotEntity plotEntity = plotRepository.findPlotEntityByPlotUuid(dto.getPlotUuid())
            .orElseThrow(() -> new NoSuchPlotException());

        // 바뀐내용만 업데이트 : null이면 x -> 내일 수정

        /*plotEntity.updatePlot(dto.getPlotTitle(), dto.getStory(), dto.getBeginning(), dto.getRising(),
            dto.getCrisis(), dto.getClimax(), dto.getEnding());*/

        // 저장
        plotRepository.save(plotEntity);
    }
}
