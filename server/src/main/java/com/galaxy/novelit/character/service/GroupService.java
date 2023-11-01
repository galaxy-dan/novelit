package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import java.util.List;

public interface GroupService {
    GroupDtoRes getGroup(String groupUuid);
    void createGroup(GroupDtoReq dto);
    void deleteGroup(String groupUuid);
    void updateGroupName(String groupUuid, String newName);

}
