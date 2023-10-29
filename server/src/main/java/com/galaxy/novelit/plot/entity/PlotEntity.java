//package com.galaxy.novelit.plot.entity;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import java.util.UUID;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//@Entity(name = "plot")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Builder
//public class PlotEntity {
//    @Id
//    @Column(name = "plot_id")
//    private Long plotId;
//    @Id
//    @Column(name = "workspace_uuid")
//    private UUID workspaceUuid;
//    @Column(name = "plot_uuid")
//    private UUID plotUuid;
//    @Column(name = "plot_title")
//    private String plotTitle;
//    @Column(name = "story")
//    private String story;
//    @Column(name = "beginning")
//    private String beginning;
//    @Column(name = "rising")
//    private String rising;
//    @Column(name = "crisis")
//    private String crisis;
//    @Column(name = "climax")
//    private String climax;
//    @Column(name = "ending")
//    private String ending;
//
//    public static PlotEntity create(String plotTitle, String story, String beginning, String rising, String crisis, String climax, String ending) {
//        return PlotEntity.builder()
//            .plotTitle(plotTitle)
//            .story(story)
//            .beginning(beginning)
//            .rising(rising)
//            .crisis(crisis)
//            .climax(climax)
//            .ending(ending)
//            .build();
//    }
//
//
//}
