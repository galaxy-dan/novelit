package com.galaxy.novelit.plot.response;

import com.galaxy.novelit.plot.entity.Plot;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public record PlotDetailResponse (String plotTitle,
                                  String story,
                                  String beginning,
                                  String rising,
                                  String crisis,
                                  String climax,
                                  String ending){

    public PlotDetailResponse (Plot plot) {
        this(plot.getPlotTitle(), plot.getStory(), plot.getBeginning(), plot.getRising(),
                plot.getCrisis(), plot.getClimax(), plot.getEnding());
    }
}
