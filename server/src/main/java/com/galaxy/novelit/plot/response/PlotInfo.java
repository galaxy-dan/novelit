package com.galaxy.novelit.plot.response;

import com.galaxy.novelit.plot.entity.Plot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public record PlotInfo (String plotUuid, String plotTitle, String story){

    public static PlotInfo plotInfo(Plot plot) {
        return new PlotInfo(plot.getPlotUuid(), plot.getPlotTitle(), plot.getStory());
    }
}

