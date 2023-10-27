package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import org.springframework.stereotype.Service;

@Service
public class GroupServiceImpl implements GroupService {

    @Override
    public GroupDtoRes getGroup(String groupUuid) {
        return null;
    }

    @Override
    public void createGroup() {

    }

    @Override
    public void deleteGroup(String groupUuid) {

    }

    @Override
    public GroupDtoRes updateGroup(GroupDtoReq groupDtoReq) {
        return null;
    }
}
