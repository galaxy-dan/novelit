package com.galaxy.novelit.plot.controller;

import com.galaxy.novelit.plot.dto.request.PlotListRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotListResponseDto;
import com.galaxy.novelit.plot.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/plot")
@RequiredArgsConstructor
public class PlotController {

    private final PlotService plotService;

    @GetMapping
    public ResponseEntity<PlotListResponseDto> getPlotList(
        @RequestBody PlotListRequestDto plotListRequestDto) {
        return ResponseEntity.ok(plotService.getPlotList(plotListRequestDto));
    }

    @PostMapping
    public ResponseEntity<Void> createPlot(@RequestBody PlotCreateRequestDto plotCreateRequestDto) {
        plotService.createPlot(plotCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<PlotDetailsResponseDto> getPlotDetails(@RequestParam("plotUuid") String plotUuid) {
        return ResponseEntity.ok(plotService.getPlotDetails(plotUuid));
    }

    @PutMapping
    public ResponseEntity<Void> savePlot(@RequestBody PlotSaveRequestDto plotSaveRequestDto) {
        plotService.savePlot(plotSaveRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlot(@RequestParam("plotUuid") String plotUuid) {
        plotService.deletePlot(plotUuid);
        return ResponseEntity.ok().build();
    }
}
