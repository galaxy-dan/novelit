package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSearchInfoResDTO;
import com.galaxy.novelit.character.dto.res.CharacterSearchInfoResDTO.CharacterSearchInfoResDTOBuilder;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterThumbnailDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterThumbnailDtoRes.CharacterThumbnailDtoResBuilder;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes.RelationDto;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.CharacterEntity.CharacterEntityBuilder;
import com.galaxy.novelit.character.entity.GroupEntity;
import com.galaxy.novelit.character.entity.RelationEntity;
import com.galaxy.novelit.character.entity.RelationEntity.Relation;
import com.galaxy.novelit.character.repository.CharacterRepository;
import com.galaxy.novelit.character.repository.GroupRepository;
import com.galaxy.novelit.character.repository.RelationRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final GroupRepository groupRepository;
    private final RelationRepository relationRepository;


    @Transactional(readOnly = true)
    @Override
    public CharacterDtoRes getCharacterInfo(String characterUUID, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);

        CharacterDtoRes dto = new CharacterDtoRes();
        dto.setGroupUUID(character.getGroupUUID());
        dto.setCharacterName(character.getCharacterName());
        dto.setCharacterUUID(character.getCharacterUUID());
        dto.setInformation(character.getInformation());
        dto.setDescription(character.getDescription());
        dto.setRelations(character.getRelationship().getRelations());
        dto.setDeleted(character.isDeleted());
        dto.setCharacterImage(character.getCharacterImage());

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterThumbnailDtoRes> getCharacters(String groupUUID, String userUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(groupUUID);
//            .orElseThrow(() -> new NoSuchElementFoundException("없는 그룹입니디."));

        List<CharacterThumbnailDtoRes> characterThumbnailInfoList = new ArrayList<>();

        for (CharacterEntity character : characters) {
            List<Map<String, String>> infos = character.getInformation();
            CharacterThumbnailDtoResBuilder characterThumbnailDtoRes = CharacterThumbnailDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterImage(character.getCharacterImage())
                .characterName(character.getCharacterName());

//            if(infos != null) {
            if(infos.size() == 0) {
                characterThumbnailDtoRes.information(new ArrayList<>());
            } else { // 1 번에 map으로 저장
                Map<String, String> mappedInfo = infos.get(0);
                List<Map<String, String>> returnval = new ArrayList<>();
                returnval.add(mappedInfo);
                characterThumbnailDtoRes.information(returnval);

            }

//            }
            characterThumbnailInfoList.add(characterThumbnailDtoRes.build());
        }

        return characterThumbnailInfoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterThumbnailDtoRes> getTopCharacter(String workspaceUUID, String userUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByWorkspaceUUIDAndGroupUUIDIsNull(workspaceUUID);
        List<CharacterThumbnailDtoRes> dto = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterThumbnailDtoRes characterThumbnailDtoRes = CharacterThumbnailDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterName(character.getCharacterName())
                .information(character.getInformation())
                .characterImage(character.getCharacterImage())
                .build();
            dto.add(characterThumbnailDtoRes);
        }

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getNoGroupCharacters(String workspaceUUID, String userUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByWorkspaceUUIDAndGroupUUIDIsNull(workspaceUUID);
        List<CharacterSimpleDtoRes> dto = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterSimpleDtoRes characterSimpleDtoRes = CharacterSimpleDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterName(character.getCharacterName())
                .build();
            dto.add(characterSimpleDtoRes);
        }

        return dto;
    }

    @Transactional
    @Override
    public void createCharacter(CharacterCreateDtoReq dto, String userUUID) {
        String characterUUID = UUID.randomUUID().toString();

        String groupUUID = dto.getGroupUUID();
        // 그룹이 조회되지 않을 때 그룹UUID를 null로 변경
        if (groupRepository.findByGroupUUID(groupUUID) == null) {
            groupUUID = null;
        }

        RelationEntity newRelation = RelationEntity.builder()
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .relations(dto.getRelations())
            .build();

        relationRepository.save(newRelation);

        CharacterEntityBuilder newCharacter = CharacterEntity.builder()
            .userUUID(userUUID)
            .workspaceUUID(dto.getWorkspaceUUID())
            .groupUUID(groupUUID)
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())

            .relationship(newRelation)
            .characterImage(dto.getCharacterImage());

        if(dto.getInformation() != null) {
            newCharacter.information(dto.getInformation());
        } else {
            newCharacter.information(new ArrayList<>());
        }

        characterRepository.save(newCharacter.build());

        // 소속 그룹의 엔티티에 추가
        if (groupUUID != null) {
            GroupEntity parentGroup = groupRepository.findByGroupUUID(groupUUID);
            CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
            parentGroup.addChildCharacter(character);

            groupRepository.save(parentGroup);
        }
    }

    @Transactional
    @Override
    public void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        RelationEntity relation = relationRepository.findByCharacterUUID(characterUUID);
        RelationEntity newRelation;
        CharacterEntityBuilder newCharacter;

        newRelation = RelationEntity.builder()
            .id(relation.getId())
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .relations(dto.getRelations())
            .build();

        relationRepository.save(newRelation);

        newCharacter = CharacterEntity.builder()
            .characterId(character.getCharacterId())
            .userUUID(userUUID)
            .workspaceUUID(character.getWorkspaceUUID())
            .groupUUID(dto.getGroupUUID())
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(newRelation)
            .characterImage(dto.getCharacterImage())
            .deleted(character.isDeleted());

        if(dto.getInformation() != null) {
            newCharacter.information(dto.getInformation());
        } else {
            newCharacter.information(new ArrayList<>());
        }

        characterRepository.save(newCharacter.build());
    }

    @Transactional
    @Override
    public void moveCharacter(String characterUUID, String groupUUID, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        GroupEntity parentGroup = groupRepository.findByGroupUUID(character.getGroupUUID());
        GroupEntity newParentGroup = groupRepository.findByGroupUUID(groupUUID);
        parentGroup.removeChildCharacter(character);
        newParentGroup.addChildCharacter(character);
        character.moveCharacter(groupUUID);

        characterRepository.save(character);
        groupRepository.save(parentGroup);
        groupRepository.save(newParentGroup);
    }


    @Transactional
    @Override
    public void deleteCharacter(String characterUUID, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        GroupEntity parentGroup = groupRepository.findByGroupUUID(character.getGroupUUID());

        character.deleteCharacter();
        parentGroup.addChildCharacter(character);

        characterRepository.save(character);
        relationRepository.deleteByCharacterUUID(characterUUID);
        groupRepository.save(parentGroup);
    }

    @Transactional
    @Override
    public List<CharacterSearchInfoResDTO> searchCharacter(String workspaceUUID, String characterName) {
        List<CharacterEntity> characters = characterRepository
                .findAllByWorkspaceUUIDAndCharacterNameLike(workspaceUUID, characterName);
        System.out.println(characters.size());
        List<CharacterSearchInfoResDTO> characterInfoList = new ArrayList<>();

        for (CharacterEntity character : characters) {


            CharacterSearchInfoResDTOBuilder characterSearchInfoResDTO = CharacterSearchInfoResDTO.builder()
                .characterUUID(character.getCharacterUUID())
                .characterImage(character.getCharacterImage())
                .characterName(character.getCharacterName());



            if(character.getGroupUUID() != null) {
                String groupName = groupRepository.findByGroupUUID(character.getGroupUUID()).getGroupName();
                characterSearchInfoResDTO.groupName(groupName).groupUUID(character.getGroupUUID());;
            } else {
                characterSearchInfoResDTO.groupName("").groupUUID("");
            }

            List<Map<String, String>> infos = character.getInformation();
//            if(infos != null) {
            if(infos.size() == 0) {
                characterSearchInfoResDTO.information(new ArrayList<Map<String, String>>());
            } else {
                characterSearchInfoResDTO.information(infos);
            }

//            }

            characterInfoList.add(characterSearchInfoResDTO.build());
        }
        return characterInfoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<RelationDtoRes> getRelationships() {
        List<RelationEntity> allRelation = relationRepository.findAll();
        List<RelationDtoRes> allDto = new ArrayList<>();

        for (RelationEntity relation : allRelation) {

            // 캐릭터(주체,기준) 정보 조회
            String characterUUID = relation.getCharacterUUID();
            String characterName = relation.getCharacterName();
            CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
            String characterImage = character.getCharacterImage();
            String groupUUID = character.getGroupUUID();
            String groupName = null;
            if (groupRepository.findByGroupUUID(groupUUID) != null) {
                groupName = groupRepository.findByGroupUUID(groupUUID).getGroupName();
            }

            // 타켓 정보 조회
            List<RelationDto> targetList = new ArrayList<>();

            if (relation.getRelations() != null) {
                for (Relation target : relation.getRelations()) {
                    String targetUUID = target.getTargetUUID();
                    String targetName = target.getTargetName();
                    CharacterEntity targetCharacter = characterRepository.findByCharacterUUID(targetUUID);
                    String targetImage = null;
                    String targetGroupUUID = null;
                    String targetGroupName = null;

                    if (targetCharacter != null) {
                        targetGroupUUID = targetCharacter.getGroupUUID();
                        targetImage = targetCharacter.getCharacterImage();
                    }

                    GroupEntity targetGroup = groupRepository.findByGroupUUID(targetGroupUUID);
                    if (targetGroup != null) {
                        targetGroupName = targetGroup.getGroupName();
                    }
                    String content = target.getContent();

                    RelationDto targetDto = RelationDto.builder()
                        .targetUUID(targetUUID)
                        .targetName(targetName)
                        .targetImage(targetImage)
                        .targetGroupUUID(targetGroupUUID)
                        .targetGroupName(targetGroupName)
                        .content(content)
                        .build();

                    targetList.add(targetDto);
                }
            }

            // 조회한 정보 모두 dto에 저장
            RelationDtoRes dto = RelationDtoRes.builder()
                .characterUUID(characterUUID)
                .characterName(characterName)
                .characterImage(characterImage)
                .groupUUID(groupUUID)
                .groupName(groupName)
                .relations(targetList)
                .build();

            allDto.add(dto);
        }

        return allDto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getAllCharacters(String workspaceUUID, String userUUID) {
        List<CharacterEntity> allCharacters = characterRepository.findAllByWorkspaceUUIDAndDeletedIsFalse(workspaceUUID);
        List<CharacterSimpleDtoRes> dtoList = new ArrayList<>();

        for (CharacterEntity character : allCharacters) {
            CharacterSimpleDtoRes dto = CharacterSimpleDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterName(character.getCharacterName())
                .build();

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Transactional
    @Override
    public void moveCharacterNode(String characterUUID, Double x, Double y, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);

        if (character.getCharacterNode() == null) {
            Map<String, Double> characterNode = new HashMap<>();
            characterNode.put("x", x);
            characterNode.put("y", y);
            character.setCharacterNode(characterNode);
        }
        character.moveCharacterNode(x, y);

        characterRepository.save(character);
    }


}
