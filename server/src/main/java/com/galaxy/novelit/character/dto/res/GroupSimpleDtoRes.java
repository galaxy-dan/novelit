package com.galaxy.novelit.character.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
public class GroupSimpleDtoRes {
    private String groupUUID;
    private String groupName;
}
