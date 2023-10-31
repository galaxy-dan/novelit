package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.common.exception.NoSuchPlotException;
import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotListRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import com.galaxy.novelit.plot.entity.PlotEntity;
import com.galaxy.novelit.plot.repository.PlotRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Service
public class PlotServiceImpl implements PlotService{

    private final PlotRepository plotRepository;

    @Override
    public PlotListResponseDto getPlotList(PlotListRequestDto plotListRequestDto) {
        if (plotListRequestDto.getKeyword() == null) {
            List<PlotEntity> plotEntities = plotRepository.findAllByWorkspaceUuid(
                    plotListRequestDto.getWorkspaceUuid())
                .orElseThrow(() -> new NoSuchPlotException());

            return PlotListResponseDto.entityToDto(plotEntities);
        }
        return null;
    }

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

        log.info("plotEntity : {}" , plotEntity.getEnding());

        // 바뀐내용만 업데이트 : null이면 x -> 내일 수정
        if (dto.getPlotTitle() != null) {
            plotEntity.updatePlotTitle(dto.getPlotTitle());
        }

        if (dto.getStory() != null) {
            plotEntity.updateStory(dto.getStory());
        }

        if (dto.getBeginning() != null) {
            plotEntity.updateBeginning(dto.getBeginning());
        }

        if (dto.getRising() != null) {
            plotEntity.updateRising(dto.getRising());
        }

        if (dto.getCrisis() != null) {
            plotEntity.updateCrisis(dto.getCrisis());
        }

        if (dto.getClimax() != null) {
            plotEntity.updateClimax(dto.getClimax());
        }

        if (dto.getEnding() != null) {
            plotEntity.updateEnding(dto.getEnding());
        }

        log.info("plotEntity : {}" , plotEntity.getEnding());

        /*plotEntity.updatePlot(dto.getPlotTitle(),
            dto.getStory(),
            dto.getBeginning(),
            dto.getRising(),
            dto.getCrisis(),
            dto.getClimax(),
            dto.getEnding());*/
        // 저장
        plotRepository.save(plotEntity);
    }

    @Override
    @Transactional
    public void deletePlot(String plotUuid) {
        // DB에서 삭제
        plotRepository.deletePlotEntitiesByPlotUuid(plotUuid)
            .orElseThrow(() -> new NoSuchPlotException());
    }
}
