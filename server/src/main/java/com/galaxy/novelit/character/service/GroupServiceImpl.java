package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.repository.GroupRepository;
import java.util.UUID;
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
        GroupEntity group = groupRepository.findByGroupUuid(groupUuid);

//        삭제된 그룹 처리
//        if (group.isDeleted()) {
//            return ;
//        }

        GroupDtoRes dto = new GroupDtoRes();
        dto.setGroupUuid(group.getGroupUuid());
        dto.setGroupName(group.getGroupName());
        dto.setChildUuid(group.getChildUuid());
        dto.setUserUuid(group.getUserUuid());
        dto.setParentUuid(group.getParentUuid());
        dto.setWorkspaceUuid(group.getWorkspaceUuid());

        return dto;
    }

    @Transactional
    @Override
    public void createGroup(GroupDtoReq dto) {
        String groupUuid = UUID.randomUUID().toString();

        GroupEntity newGroup = GroupEntity.builder()
            .groupUuid(groupUuid)
            .groupName(dto.getGroupName())
            .workspaceUuid(dto.getWorkspaceUuid())
            .userUuid(dto.getUserUuid())
            .parentUuid(dto.getParentUuid())
            .build();

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void deleteGroup(String groupUuid) {
        String groupId = groupRepository.findByGroupUuid(groupUuid).getGroupId();

        GroupEntity newGroup = GroupEntity.builder()
            .groupId(groupId)
            .isDeleted(true)
            .build();

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void updateGroupName(String groupUuid, String newName) {
        String groupId = groupRepository.findByGroupUuid(groupUuid).getGroupId();

        GroupEntity newGroup = GroupEntity.builder()
            .groupId(groupId)
            .groupName(newName)
            .build();

        groupRepository.save(newGroup);
    }
}
