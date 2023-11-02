package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;

public interface GroupService {
    GroupDtoRes getGroup(String groupUUID);
    void createGroup(GroupCreateDtoReq dto);
    void deleteGroup(String groupUUID);
    void updateGroupName(String groupUUID, String newName);

}
