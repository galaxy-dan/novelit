package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.AllGroupsCharactersDtoRes;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
import com.galaxy.novelit.character.repository.GroupRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final CharacterRepository characterRepository;

    @Transactional(readOnly = true)
    @Override
    public GroupDtoRes getGroupInfo(String groupUUID, String userUUID) {
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
    public List<GroupSimpleDtoRes> getTopGroup(String workspaceUUID, String userUUID) {
        List<GroupEntity> groups = groupRepository.findAllByWorkspaceUUIDAndParentGroupUUIDIsNull(workspaceUUID);
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
    public void createGroup(GroupCreateDtoReq dto, String userUUID) {
        String groupUUID = UUID.randomUUID().toString();
        String parentGroupUUID = dto.getParentGroupUUID();
        GroupEntity parentGroup = groupRepository.findByGroupUUID(parentGroupUUID);
        GroupEntity newGroup;

        // dto에 parentGroupUUID가 잘못된 값이 들어왔을 때, null 값으로 처리
        if (parentGroup == null) {
            newGroup = GroupEntity.builder()
                .userUUID(userUUID)
                .workspaceUUID(dto.getWorkspaceUUID())
                .groupUUID(groupUUID)
                .groupName(dto.getGroupName())
                .parentGroupUUID(null)
                .childGroups(new ArrayList<>())
                .childCharacters(new ArrayList<>())
                .build();

            groupRepository.save(newGroup);
        }
        else {
            newGroup = GroupEntity.builder()
                .userUUID(userUUID)
                .workspaceUUID(dto.getWorkspaceUUID())
                .groupUUID(groupUUID)
                .groupName(dto.getGroupName())
                .parentGroupUUID(parentGroupUUID)
                .childGroups(new ArrayList<>())
                .childCharacters(new ArrayList<>())
                .build();

            groupRepository.save(newGroup);

            // 부모 그룹의 childGroups 리스트에 자식 추가
            parentGroup.addChildGroup(groupRepository.findByGroupUUID(groupUUID));
            groupRepository.save(parentGroup);
        }
    }

    @Transactional
    @Override
    public void deleteGroup(String groupUUID, String userUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        group.deleteGroup();
        groupRepository.save(group);

        GroupEntity parentGroup = groupRepository.findByGroupUUID(group.getParentGroupUUID());

        // 부모그룹의 childGroups 리스트에 자식 제거
        if (parentGroup != null) {
            parentGroup.removeChildGroup(groupRepository.findByGroupUUID(groupUUID));
            groupRepository.save(parentGroup);
        }
    }

    @Transactional
    @Override
    public void updateGroupName(String groupUUID, String newName, String userUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        group.updateGroupName(newName);
        groupRepository.save(group);
    }

    @Transactional(readOnly = true)
    @Override
    public List<GroupSimpleDtoRes> getAllGroups(String workspaceUUID, String userUUID) {
        List<GroupEntity> allGroups = groupRepository.findAllByWorkspaceUUIDAndDeletedIsFalse(workspaceUUID);
        List<GroupSimpleDtoRes> dtoList = new ArrayList<>();

        for (GroupEntity group : allGroups) {
            GroupSimpleDtoRes dto = GroupSimpleDtoRes.builder()
                .groupUUID(group.getGroupUUID())
                .groupName(group.getGroupName())
                .build();

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<AllGroupsCharactersDtoRes> getAllGroupsAndCharacters(String workspaceUUID, String userUUID) {
        List<GroupEntity> allGroups = groupRepository.findAllByWorkspaceUUIDAndDeletedIsFalse(workspaceUUID);
        List<AllGroupsCharactersDtoRes> dtoList = new ArrayList<>();

        for (GroupEntity group : allGroups) {

            List<GroupEntity> childGroups = group.getChildGroups();
            Map<String, String> childGroupMap = new LinkedHashMap<>();
            if (childGroups != null) {
                for (GroupEntity childGroup : childGroups) {
                    childGroupMap.put(childGroup.getGroupUUID(), childGroup.getGroupName());
                }
            }

            List<CharacterEntity> childCharacters = group.getChildCharacters();
            Map<String, String> childCharacterMap = new LinkedHashMap<>();
            if (childCharacters != null) {
                for (CharacterEntity childCharacter : childCharacters) {
                    childCharacterMap.put(childCharacter.getCharacterUUID(), childCharacter.getCharacterName());
                }
            }

            AllGroupsCharactersDtoRes dto = AllGroupsCharactersDtoRes.builder()
                .groupUUID(group.getGroupUUID())
                .groupName(group.getGroupName())
                .childGroups(childGroupMap)
                .childCharacters(childCharacterMap)
                .build();

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    @Override
    public void moveGroupNode(String groupUUID, Double x, Double y, String userUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);

        if (group.getGroupNode() == null) {
            Map<String, Double> groupNode = new HashMap<>();
            groupNode.put("x", x);
            groupNode.put("y", y);
            group.setGroupNode(groupNode);
        }
        group.moveGroupNode(x, y);

        groupRepository.save(group);
    }

}
