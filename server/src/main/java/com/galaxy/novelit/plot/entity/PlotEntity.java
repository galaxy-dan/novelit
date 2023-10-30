package com.galaxy.novelit.plot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class PlotEntity {
    @Id
    @Column(name = "plot_id")
    private Long plotId;
    @Id
    @Column(name = "workspace_uuid")
    private String workspaceUuid;
    @Column(name = "plot_uuid")
    private UUID plotUuid;

    @Column(name = "plot_title")
    @ColumnDefault("제목 없음")
    private String plotTitle;
    @Column(name = "story")
    @ColumnDefault("")
    private String story;
    @Column(name = "beginning")
    @ColumnDefault("")
    private String beginning;
    @Column(name = "rising")
    @ColumnDefault("")
    private String rising;
    @Column(name = "crisis")
    @ColumnDefault("")
    private String crisis;
    @Column(name = "climax")
    @ColumnDefault("")
    private String climax;
    @Column(name = "ending")
    @ColumnDefault("")
    private String ending;

    public static PlotEntity create(String plotTitle, String story, String beginning, String rising, String crisis, String climax, String ending) {
        return PlotEntity.builder()
            .plotTitle(plotTitle)
            .story(story)
            .beginning(beginning)
            .rising(rising)
            .crisis(crisis)
            .climax(climax)
            .ending(ending)
            .build();
    }


}
