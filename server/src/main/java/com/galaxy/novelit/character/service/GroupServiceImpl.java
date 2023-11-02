package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.repository.GroupRepository;
import java.util.ArrayList;
import java.util.List;
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
    public GroupDtoRes getGroupInfo(String groupUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);

//        삭제된 그룹 처리
//        if (group.isDeleted()) {
//            return ;
//        }

        GroupDtoRes dto = new GroupDtoRes();
        dto.setGroupUUID(group.getGroupUUID());
        dto.setGroupName(group.getGroupName());
        dto.setChildUUID(group.getChildUUID());
        dto.setParentUUID(group.getParentUUID());
        dto.setWorkspaceUUID(group.getWorkspaceUUID());
        dto.setCharactersInfo(group.getCharactersInfo());
        dto.setDeleted(group.isDeleted());

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupSimpleDtoRes> getTopGroup() {
        List<GroupEntity> groups = groupRepository.findAllByParentUUID(null);
        List<GroupSimpleDtoRes> dto = new ArrayList<>();

        for (GroupEntity group : groups) {
            GroupSimpleDtoRes groupSimpleDtoRes = GroupSimpleDtoRes.builder()
                .groupUUID(group.getGroupUUID())
                .groupName(group.getGroupName())
                .build();
            dto.add(groupSimpleDtoRes);
        }

        return dto;
    }


    @Transactional
    @Override
    public void createGroup(GroupCreateDtoReq dto) {
        String groupUUID = UUID.randomUUID().toString();

        // parentUUID가 db에 있는지 확인
        String parentUUID = dto.getParentUUID();
        if (groupRepository.findByGroupUUID(parentUUID) == null) {
            return ;
        }

        GroupEntity newGroup = GroupEntity.builder()
            .groupUUID(groupUUID)
            .groupName(dto.getGroupName())
            .workspaceUUID(dto.getWorkspaceUUID())
            .userUUID(dto.getUserUUID())
            .parentUUID(parentUUID)
            .build();

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void deleteGroup(String groupUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);

        GroupEntity newGroup = GroupEntity.builder()
            .groupId(group.getGroupId())
            .groupUUID(group.getGroupUUID())
            .userUUID(group.getUserUUID())
            .workspaceUUID(group.getWorkspaceUUID())
            .groupName(group.getGroupName())
            .parentUUID(group.getParentUUID())
            .childUUID(group.getChildUUID())
            .charactersInfo(group.getCharactersInfo())
            .isDeleted(true)
            .build();

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void updateGroupName(String groupUUID, String newName) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);

        GroupEntity newGroup = GroupEntity.builder()
            .groupId(group.getGroupId())
            .groupUUID(group.getGroupUUID())
            .userUUID(group.getUserUUID())
            .workspaceUUID(group.getWorkspaceUUID())
            .groupName(newName)
            .parentUUID(group.getParentUUID())
            .childUUID(group.getChildUUID())
            .charactersInfo(group.getCharactersInfo())
            .isDeleted(group.isDeleted())
            .build();

        groupRepository.save(newGroup);
    }
}
