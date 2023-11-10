package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import java.util.List;

public interface GroupService {
    GroupDtoRes getGroupInfo(String groupUUID, String userUUID);
    List<GroupSimpleDtoRes> getTopGroup(String workspaceUUID, String userUUID);
    void createGroup(GroupCreateDtoReq dto, String userUUID);
    void deleteGroup(String groupUUID, String userUUID);
    void updateGroupName(String groupUUID, String newName, String userUUID);

}
