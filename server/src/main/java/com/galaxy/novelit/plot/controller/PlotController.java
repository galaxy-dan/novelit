package com.galaxy.novelit.plot.controller;

import com.galaxy.novelit.plot.request.PlotUpdateRequest;
import com.galaxy.novelit.plot.response.PlotDetailResponse;
import com.galaxy.novelit.plot.request.PlotCreateRequest;
import com.galaxy.novelit.plot.response.PlotListResponse;
import com.galaxy.novelit.plot.service.PlotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/plot")
@RequiredArgsConstructor
public class PlotController {

    private final PlotService plotService;

    @GetMapping
    public ResponseEntity<PlotListResponse> getPlotList(@RequestParam("workspaceUuid") String workspaceUuid,
                                                        @RequestParam(value = "keyword", required = false) String keyword) {
        if (isKeywordPresent(keyword)) {
            return ResponseEntity.ok(plotService.getPlotListByKeyword(workspaceUuid, keyword));
        }
        return ResponseEntity.ok(plotService.getPlotList(workspaceUuid));
    }

    private boolean isKeywordPresent(String keyword){
        return keyword != null;
    }

    @PostMapping
    public ResponseEntity<Void> createPlot(@RequestBody PlotCreateRequest plotCreateRequest) {
        plotService.createPlot(plotCreateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/detail")
    public ResponseEntity<PlotDetailResponse> getPlotDetails(@RequestParam("plotUuid") String plotUuid) {
        PlotDetailResponse plotDetail = plotService.getPlotDetails(plotUuid);
        return ResponseEntity.ok(plotDetail);
    }

    @PatchMapping
    public ResponseEntity<Void> updatePlot(@RequestBody PlotUpdateRequest plotUpdateRequest) {
        plotService.updatePlot(plotUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePlot(@RequestParam("plotUuid") String plotUuid) {
        plotService.deletePlot(plotUuid);
        return ResponseEntity.ok().build();
    }
}
