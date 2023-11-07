package com.galaxy.novelit.plot.entity;

import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.request.PlotSaveRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.ObjectUtils;

@Entity(name = "plot")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@DynamicInsert
@DynamicUpdate
public class PlotEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plot_id", nullable = false)
    private Long plotId;
    @Column(name = "workspace_uuid", nullable = false)
    private String workspaceUuid;
    @Column(name = "plot_uuid", nullable = false)
    private String plotUuid;

    @Column(name = "plot_title", length = 500)
    @ColumnDefault("'제목 없음'")
    private String plotTitle;
    @Column(name = "story",  length = 2000)
    @ColumnDefault("''")
    private String story;
    @Column(name = "beginning", length = 2000)
    @ColumnDefault("''")
    private String beginning;
    @Column(name = "rising", length = 2000)
    @ColumnDefault("''")
    private String rising;
    @Column(name = "crisis", length = 2000)
    @ColumnDefault("''")
    private String crisis;
    @Column(name = "climax", length = 2000)
    @ColumnDefault("''")
    private String climax;
    @Column(name = "ending", length = 2000)
    @ColumnDefault("''")
    private String ending;

    public PlotEntity(String workspaceUuid, String plotUuid, String plotTitle, String story,
        String beginning, String rising, String crisis, String climax, String ending) {
        this.workspaceUuid = workspaceUuid;
        this.plotUuid = plotUuid;
        this.plotTitle = plotTitle;
        this.story = story;
        this.beginning = beginning;
        this.rising = rising;
        this.crisis = crisis;
        this.climax = climax;
        this.ending = ending;
    }

    // dto -> entity
    public static PlotEntity create(String plotString, PlotCreateRequestDto dto) {
        return PlotEntity.builder()
            .workspaceUuid(dto.getWorkspaceUuid())
            .plotUuid(plotString)
            .plotTitle(dto.getPlotTitle())
            .story(dto.getStory())
            .beginning(dto.getBeginning())
            .rising(dto.getRising())
            .crisis(dto.getCrisis())
            .climax(dto.getClimax())
            .ending(dto.getEnding())
            .build();
    }

    public void updatePlotTitle(String plotTitle) {
        this.plotTitle = plotTitle;
    }

    public void updateStory(String story) {
        this.story = story;
    }

    public void updateBeginning(String beginning) {
        this.beginning = beginning;
    }

    public void updateRising(String rising) {
        this.rising = rising;
    }

    public void updateCrisis(String crisis) {
        this.crisis = crisis;
    }

    public void updateClimax(String climax) {
        this.climax = climax;
    }

    public void updateEnding(String ending) {
        this.ending = ending;
    }

    public void updatePlot(String plotTitle, String story, String beginning, String rising, String crisis,
        String climax, String ending) {
        this.plotTitle = plotTitle;
        this.story = story;
        this.beginning = beginning;
        this.rising = rising;
        this.crisis = crisis;
        this.climax = climax;
        this.ending = ending;
    }


    public void plotInfo(PlotSaveRequestDto saveDto) {
        // null 값 체크
        if (ObjectUtils.isEmpty(saveDto.getPlotTitle())) {

        }
    }

}
