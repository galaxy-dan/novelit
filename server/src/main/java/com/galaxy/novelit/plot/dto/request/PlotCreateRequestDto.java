package com.galaxy.novelit.plot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlotCreateRequestDto {
    private String workspaceUuid;
    private String plotTitle;
    private String story;
    private String beginning;
    private String rising;
    private String crisis;
    private String climax;
    private String ending;
}
