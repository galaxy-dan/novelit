package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.AllGroupsCharactersDtoRes;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleWithNodeDtoRes;
import java.util.List;

public interface GroupService {
    GroupDtoRes getGroupInfo(String groupUUID, String userUUID, String workspaceUUID);
    List<GroupSimpleDtoRes> getTopGroup(String workspaceUUID, String userUUID);
    void createGroup(GroupCreateDtoReq dto, String userUUID);
    void deleteGroup(String groupUUID, String userUUID, String workspaceUUID);
    void updateGroupName(String groupUUID, String newName, String userUUID, String workspaceUUID);
    List<GroupSimpleWithNodeDtoRes> getAllGroupsWithNode(String workspaceUUID, String userUUID);
    List<AllGroupsCharactersDtoRes> getAllGroupsAndCharacters(String workspaceUUID, String userUUID);
    void moveGroupNode(String groupUUID, Double x, Double y, String userUUID, String workspaceUUID);
}
