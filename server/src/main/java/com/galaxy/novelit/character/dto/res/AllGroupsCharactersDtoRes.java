package com.galaxy.novelit.character.dto.res;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AllGroupsCharactersDtoRes {
    String groupUUID;
    String groupName;
    Map<String, String> childGroups;
    Map<String, String> childCharacters;
}
