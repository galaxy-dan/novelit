package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;

public interface GroupService {
    GroupDtoRes getGroup(String groupUuid);
    void createGroup();
    void deleteGroup(String groupUuid);
    GroupDtoRes updateGroup(GroupDtoReq groupDtoReq);

}
