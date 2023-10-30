package com.galaxy.novelit.author.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorAndWorkspaceResDTO {
    private String nickname;
    private List<?> workspaces;
}
