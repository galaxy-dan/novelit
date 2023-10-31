package com.galaxy.novelit.plot.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlotListRequestDto {
    private String workspaceUuid; // 작품 id
    private String keyword; // 검색 키워드
}
