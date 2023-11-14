package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.AllGroupsCharactersDtoRes;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
import com.galaxy.novelit.character.repository.GroupRepository;
import com.galaxy.novelit.character.repository.RelationRepository;
import com.galaxy.novelit.common.exception.DeletedElementException;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
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
    private final RelationRepository relationRepository;

    @Transactional(readOnly = true)
    @Override
    public GroupDtoRes getGroupInfo(String groupUUID, String userUUID, String workspaceUUID) {
        GroupEntity group = groupRepository.findByGroupUUIDAndWorkspaceUUID(groupUUID, workspaceUUID);
        checkGroupException(group);

        List<GroupEntity> childGroups = groupRepository.findAllByParentGroupUUIDAndDeletedIsFalse(groupUUID);
        List<CharacterEntity> childCharacters = characterRepository.findAllByGroupUUIDAndDeletedIsFalse(groupUUID);

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
        List<GroupEntity> groups = groupRepository.findAllByWorkspaceUUIDAndParentGroupUUIDIsNullAndDeletedIsFalse(workspaceUUID);
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
        // UUID 프론트에서 받아서 사용
//        String groupUUID = UUID.randomUUID().toString();

        GroupEntity newGroup;
        String parentGroupUUID = dto.getParentGroupUUID();

        if (parentGroupUUID == null) {
            newGroup = GroupEntity.builder()
                .userUUID(userUUID)
                .workspaceUUID(dto.getWorkspaceUUID())
                .groupUUID(dto.getGroupUUID())
                .groupName(dto.getGroupName())
                .parentGroupUUID(null)
                .childGroups(new ArrayList<>())
                .childCharacters(new ArrayList<>())
                .build();

            groupRepository.save(newGroup);
        }
        else {
            GroupEntity parentGroup = groupRepository.findByGroupUUID(parentGroupUUID);
            checkGroupException(parentGroup);

            newGroup = GroupEntity.builder()
                .userUUID(userUUID)
                .workspaceUUID(dto.getWorkspaceUUID())
                .groupUUID(dto.getGroupUUID())
                .groupName(dto.getGroupName())
                .parentGroupUUID(parentGroupUUID)
                .childGroups(new ArrayList<>())
                .childCharacters(new ArrayList<>())
                .build();

            groupRepository.save(newGroup);

            // 부모 그룹의 childGroups 리스트에 자식 추가
            parentGroup.addChildGroup(groupRepository.findByGroupUUID(dto.getGroupUUID()));
            groupRepository.save(parentGroup);
        }
    }

    @Transactional
    @Override
    public void deleteGroup(String groupUUID, String userUUID, String workspaceUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);

        if (group == null) {
            throw new NoSuchElementFoundException("유효하지 않은 그룹 UUID 입니다.");
        }
        if (group.isDeleted()) {
            throw new DeletedElementException("이미 삭제된 그룹 입니다.");
        }

        group.deleteGroup();
        groupRepository.save(group);

        GroupEntity parentGroup = groupRepository.findByGroupUUID(group.getParentGroupUUID());

        // 부모그룹의 childGroups 리스트에 자식 제거
        if (parentGroup != null) {
            parentGroup.removeChildGroup(groupRepository.findByGroupUUID(groupUUID));
            groupRepository.save(parentGroup);
        }

        // 그룹에 속한 자식 캐릭터 삭제
        List<CharacterEntity> childCharacters = group.getChildCharacters();
        for (CharacterEntity childCharacter : childCharacters) {
            childCharacter.deleteCharacter();
            characterRepository.save(childCharacter);
            relationRepository.deleteByCharacterUUID(childCharacter.getCharacterUUID());
        }

        // 그룹에 속한 자식 그룹들 삭제 재귀
        List<GroupEntity> childGroups = group.getChildGroups();
        for (GroupEntity childGroup : childGroups) {
            deleteGroup(childGroup.getGroupUUID(), userUUID, workspaceUUID);
        }

    }

    @Transactional
    @Override
    public void updateGroupName(String groupUUID, String newName, String userUUID, String workspaceUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        checkGroupException(group);

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
    public void moveGroupNode(String groupUUID, Double x, Double y, String userUUID, String workspaceUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        checkGroupException(group);

        if (group.getGroupNode() == null) {
            Map<String, Double> groupNode = new HashMap<>();
            groupNode.put("x", x);
            groupNode.put("y", y);
            group.setGroupNode(groupNode);
        }
        group.moveGroupNode(x, y);

        groupRepository.save(group);
    }

    public void checkGroupException(GroupEntity group) {
        if (group == null) {
            throw new NoSuchElementFoundException("유효하지 않은 그룹 UUID 입니다.");
        }
        if (group.isDeleted()) {
            throw new DeletedElementException("삭제된 그룹 입니다.");
        }
    }

}
