package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
import com.galaxy.novelit.character.repository.GroupRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.mapping.Collection;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CharacterRepository characterRepository;

    @Transactional(readOnly = true)
    @Override
    public GroupDtoRes getGroupInfo(String groupUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        List<GroupEntity> childGroups = groupRepository.findAllByParentGroupUUID(groupUUID);
        List<CharacterEntity> childCharacters = characterRepository.findAllByGroupUUID(groupUUID);

        GroupDtoRes dto = new GroupDtoRes();
        dto.setWorkspaceUUID(group.getWorkspaceUUID());
        dto.setGroupUUID(group.getGroupUUID());
        dto.setGroupName(group.getGroupName());
        dto.setParentGroupUUID(group.getParentGroupUUID());
        dto.setChildGroups(childGroups);
        dto.setChildCharacters(childCharacters);

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupSimpleDtoRes> getTopGroup() {
        List<GroupEntity> groups = groupRepository.findAllByParentGroupUUID(null);
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
        String parentUUID = dto.getParentUUID();
        GroupEntity newGroup = new GroupEntity();

        // 최상단 계층일 경우 (부모UUID가 없을 때)
        if (parentUUID == null) {
            newGroup = GroupEntity.builder()
                .parentGroupUUID(null)
                .groupUUID(groupUUID)
                .groupName(dto.getGroupName())
                .workspaceUUID(dto.getWorkspaceUUID())
                .userUUID(dto.getUserUUID())
                .build();
        }
        else {
            newGroup = GroupEntity.builder()
                .groupUUID(groupUUID)
                .groupName(dto.getGroupName())
                .workspaceUUID(dto.getWorkspaceUUID())
                .userUUID(dto.getUserUUID())
                .parentGroupUUID(parentUUID)
                .build();
        }

        groupRepository.save(newGroup);
    }

    @Transactional
    @Override
    public void deleteGroup(String groupUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        group.deleteGroup();
        groupRepository.save(group);
    }

    @Transactional
    @Override
    public void updateGroupName(String groupUUID, String newName) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        group.updateGroupName(newName);
        groupRepository.save(group);
    }
}
