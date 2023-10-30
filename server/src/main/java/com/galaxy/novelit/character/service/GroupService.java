package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;

public interface GroupService {
    GroupDtoRes getGroup(String groupUuid);
    void createGroup(GroupDtoReq dto);
    void deleteGroup(String groupUuid);
    void updateGroupName(String groupName);

}
