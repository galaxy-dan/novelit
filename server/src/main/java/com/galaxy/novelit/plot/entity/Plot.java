package com.galaxy.novelit.plot.entity;

import com.galaxy.novelit.plot.request.PlotCreateRequest;
import com.galaxy.novelit.plot.request.PlotUpdateRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@Entity(name = "plot")
@Getter
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Plot {
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

    @Builder
    public Plot(String workspaceUuid, String plotUuid, String plotTitle, String story,
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
    public static Plot create(PlotCreateRequest plotCreateRequest) {
        String plotUuid = UUID.randomUUID().toString();

        return Plot.builder()
            .workspaceUuid(plotCreateRequest.workspaceUuid())
            .plotUuid(plotUuid)
            .plotTitle(plotCreateRequest.plotTitle())
            .story(plotCreateRequest.story())
            .beginning(plotCreateRequest.beginning())
            .rising(plotCreateRequest.rising())
            .crisis(plotCreateRequest.crisis())
            .climax(plotCreateRequest.climax())
            .ending(plotCreateRequest.ending())
            .build();
    }

    public void updatePlot(PlotUpdateRequest plotUpdateRequest) {
        if (plotUpdateRequest.plotTitle() != null) {
            this.plotTitle = plotUpdateRequest.plotTitle();
        }

        if (plotUpdateRequest.story() != null) {
            this.story = plotUpdateRequest.story();
        }

        if (plotUpdateRequest.beginning() != null) {
            this.beginning = plotUpdateRequest.beginning();
        }

        if (plotUpdateRequest.rising() != null) {
            this.rising = plotUpdateRequest.rising();
        }

        if (plotUpdateRequest.crisis() != null) {
            this.crisis = plotUpdateRequest.crisis();
        }

        if (plotUpdateRequest.climax() != null) {
            this.climax = plotUpdateRequest.climax();
        }

        if (plotUpdateRequest.ending() != null) {
            this.ending = plotUpdateRequest.ending();
        }
    }
}
