package com.galaxy.novelit.plot.service;

import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import com.galaxy.novelit.plot.entity.PlotEntity;
import com.galaxy.novelit.plot.repository.PlotRepository;
import java.util.List;
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

    /*@Override
    public PlotListResponseDto getPlotList(PlotListRequestDto plotListRequestDto) {
        if (plotListRequestDto.getKeyword() == null) {
            List<PlotEntity> plotEntities = plotRepository.findAllByWorkspaceUuid(
                    plotListRequestDto.getWorkspaceUuid())
                .orElseThrow(() -> new NoSuchPlotException());

            return PlotListResponseDto.entityToDto(plotEntities);
        }
        else if (plotListRequestDto.getKeyword() != null){
            // 키워드가있을때 -> querydsl 검색
            *//*List<PlotEntity> plotEntities = plotRepository.findByWorkspaceUuidAndPlotTitleContaining(
                    plotListRequestDto.getWorkspaceUuid(),
                    plotListRequestDto.getKeyword())
                .orElseThrow(() -> new NoSuchPlotException());
            return PlotListResponseDto.entityToDto(plotEntities);*//*

        }
        return null;
    }*/

    @Override
    public PlotListResponseDto getPlotList(String workspaceUuid) {
        List<PlotEntity> plotEntities = plotRepository.findAllByWorkspaceUuid(workspaceUuid)
            .orElseThrow(() -> new NoSuchElementFoundException("플롯이 없습니다."));
        return PlotListResponseDto.entityToDto(plotEntities);
    }

    @Override
    public PlotListResponseDto getPlotListByKeyword(String workspaceUuid, String keyword) {
        List<PlotEntity> plotEntities = plotRepository.findByKeyword(workspaceUuid, keyword)
            .orElseThrow(() -> new NoSuchElementFoundException("플롯이 없습니다."));

        return PlotListResponseDto.entityToDto(plotEntities);
    }

    @Override
    public void createPlot(PlotCreateRequestDto dto) {
        UUID plotUuid = UUID.randomUUID();

        String plotString = plotUuid.toString();

        plotRepository.save(PlotEntity.create(plotString, dto));
    }

    @Override
    public PlotDetailsResponseDto getPlotDetails(String plotUuid) {

        PlotEntity plotEntity = plotRepository.findPlotEntityByPlotUuid(plotUuid)
            .orElseThrow(() -> new NoSuchElementFoundException("플롯이 없습니다."));

        return PlotDetailsResponseDto.toDto(plotEntity);
    }

    @Override
    public void savePlot(PlotSaveRequestDto dto) {
        PlotEntity plotEntity = plotRepository.findPlotEntityByPlotUuid(dto.getPlotUuid())
            .orElseThrow(() -> new NoSuchElementFoundException("플롯이 없습니다."));

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

        plotRepository.save(plotEntity);
    }

    @Override
    @Transactional
    public void deletePlot(String plotUuid) {
        plotRepository.deletePlotEntitiesByPlotUuid(plotUuid)
            .orElseThrow(() -> new NoSuchElementFoundException("플롯이 없습니다."));
    }
}
