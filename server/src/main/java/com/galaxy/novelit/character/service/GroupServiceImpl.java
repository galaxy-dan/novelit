package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional(readOnly = true)
    @Override
    public GroupDtoRes getGroup(String groupUuid) {
        GroupEntity groupEntity = groupRepository.findByGroupUuid(groupUuid);

        if (groupEntity == null) {
            return null;
        }

        return new GroupDtoRes();
    }

    @Override
    public void createGroup(GroupDtoReq dto) {

    }

    @Override
    public void deleteGroup(String groupUuid) {

    }

    @Override
    public void updateGroupName(String groupName) {

    }
}
