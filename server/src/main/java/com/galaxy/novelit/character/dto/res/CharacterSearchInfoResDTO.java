package com.galaxy.novelit.character.dto.res;

import java.util.List;
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
public class CharacterSearchInfoResDTO {
    private String characterUUID;
    private String characterName;
    private String groupUUID;
    private String groupName;
    private List<Map<String, String>> information;
    private String characterImage;
}
