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
    public GroupDtoRes getGroup(String groupUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);

//        삭제된 그룹 처리
//        if (group.isDeleted()) {
//            return ;
//        }

        GroupDtoRes dto = new GroupDtoRes();
        dto.setGroupUUID(group.getGroupUUID());
        dto.setGroupName(group.getGroupName());
        dto.setChildUUID(group.getChildUUID());
        dto.setUserUUID(group.getUserUUID());
        dto.setParentUUID(group.getParentUUID());
        dto.setWorkspaceUUID(group.getWorkspaceUUID());

        return dto;
    }

    @Transactional
    @Override
    public void createGroup(GroupDtoReq dto) {
        String groupUUID = UUID.randomUUID().toString();

        GroupEntity newGroup = GroupEntity.builder()
            .groupUUID(groupUUID)
            .groupName(dto.getGroupName())
            .workspaceUUID(dto.getWorkspaceUUID())
            .userUUID(dto.getUserUUID())
            .parentUUID(dto.getParentUUID())
            .build();

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void deleteGroup(String groupUUID) {
        String groupId = groupRepository.findByGroupUUID(groupUUID).getGroupId();

        GroupEntity newGroup = GroupEntity.builder()
            .groupId(groupId)
            .isDeleted(true)
            .build();

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void updateGroupName(String groupUUID, String newName) {
        String groupId = groupRepository.findByGroupUUID(groupUUID).getGroupId();

        GroupEntity newGroup = GroupEntity.builder()
            .groupId(groupId)
            .groupName(newName)
            .build();

        groupRepository.save(newGroup);
    }
}
