package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import java.util.List;

public interface GroupService {
    GroupDtoRes getGroupInfo(String groupUUID);
    List<GroupSimpleDtoRes> getTopGroup();
    void createGroup(GroupCreateDtoReq dto);
    void deleteGroup(String groupUUID);
    void updateGroupName(String groupUUID, String newName);

}
