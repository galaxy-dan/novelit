package com.galaxy.novelit.plot.entity;

import com.galaxy.novelit.plot.dto.request.PlotCreateRequestDto;
import com.galaxy.novelit.plot.dto.response.PlotDetailsResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity(name = "plot")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class PlotEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plot_id", nullable = false)
    private Long plotId;
    @Column(name = "workspace_uuid", nullable = false)
    private String workspaceUuid;
    @Column(name = "plot_uuid", nullable = false)
    private String plotUuid;

    @Column(name = "plot_title")
    @ColumnDefault("'제목 없음'")
    private String plotTitle;
    @Column(name = "story")
    @ColumnDefault("''")
    private String story;
    @Column(name = "beginning")
    @ColumnDefault("''")
    private String beginning;
    @Column(name = "rising")
    @ColumnDefault("''")
    private String rising;
    @Column(name = "crisis")
    @ColumnDefault("''")
    private String crisis;
    @Column(name = "climax")
    @ColumnDefault("''")
    private String climax;
    @Column(name = "ending")
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

}
