package com.galaxy.novelit.plot.dto.response;

import com.galaxy.novelit.plot.entity.PlotEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlotDetailsResponseDto {
    private String plotTitle;
    private String story;
    private String beginning;
    private String rising;
    private String crisis;
    private String climax;
    private String ending;

    // entity -> dto
    public static PlotDetailsResponseDto toDto(PlotEntity plotEntity) {
        return PlotDetailsResponseDto.builder()
            .plotTitle(plotEntity.getPlotTitle())
            .story(plotEntity.getStory())
            .beginning(plotEntity.getBeginning())
            .rising(plotEntity.getRising())
            .crisis(plotEntity.getCrisis())
            .climax(plotEntity.getClimax())
            .ending(plotEntity.getEnding())
            .build();
    }
}
