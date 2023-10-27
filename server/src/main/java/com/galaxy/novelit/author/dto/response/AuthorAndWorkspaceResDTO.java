package com.galaxy.novelit.author.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorAndWorkspaceResDTO {
    private String userUUID;
    private String email;
    private String nickname;
    private List<?> workspaces;
}
