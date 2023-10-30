package com.galaxy.novelit.plot.controller;

import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
    public ResponseEntity<Void> createPlot(@RequestBody PlotCreateRequestDto plotCreateRequestDto) {
        plotService.createPlot(plotCreateRequestDto);
        return ResponseEntity.ok().build();
    }

    /*@GetMapping
    public ResponseEntity<PlotDetailsResponseDto> getPlotDetails(@RequestParam String plotUuid) {
        plotService.getPlotDetails(plotUuid);
    }*/
}
