package com.galaxy.novelit.plot.response;

import com.galaxy.novelit.plot.entity.Plot;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record PlotListResponse (List<PlotInfo> plotInfoList){

    public static PlotListResponse plotListResponse(List<Plot> plots) {
        return new PlotListResponse(plotInfos(plots));
    }

    public static List<PlotInfo> plotInfos(List<Plot> plots) {
        return plots.stream()
                .map(PlotInfo::plotInfo)
                .collect(Collectors.toList());
    }
}
