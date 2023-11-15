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
import com.galaxy.novelit.common.exception.DeletedElementException;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.galaxy.novelit.words.dto.req.WordsCreateReqDTO;
import com.galaxy.novelit.words.entity.WordsEntity;
import com.galaxy.novelit.words.repository.WordsRepository;
import com.galaxy.novelit.words.service.WordsService;
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

    private final WordsService wordsService;

    private final CharacterRepository characterRepository;
    private final GroupRepository groupRepository;
    private final RelationRepository relationRepository;
    private final WordsRepository wordsRepository;


    @Transactional(readOnly = true)
    @Override
    public CharacterDtoRes getCharacterInfo(String characterUUID, String userUUID, String workspaceUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUIDAndWorkspaceUUID(characterUUID, workspaceUUID);

        checkCharacterException(character);

        return CharacterDtoRes.builder()
            .workspaceUUID(character.getWorkspaceUUID())
            .groupUUID(character.getGroupUUID())
            .characterUUID(character.getCharacterUUID())
            .characterName(character.getCharacterName())
            .description(character.getDescription())
            .information(character.getInformation())
            .relations(character.getRelationship().getRelations())
            .characterImage(character.getCharacterImage())
            .build();
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterThumbnailDtoRes> getCharacters(String groupUUID, String userUUID, String workspaceUUID) {
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        checkGroupException(group);

        List<CharacterEntity> characters = characterRepository.findAllByGroupUUIDAndDeletedIsFalse(groupUUID);

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
        List<CharacterEntity> characters = characterRepository.findAllByWorkspaceUUIDAndGroupUUIDIsNullAndDeletedIsFalse(workspaceUUID);
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
        List<CharacterEntity> characters = characterRepository.findAllByWorkspaceUUIDAndGroupUUIDIsNullAndDeletedIsFalse(workspaceUUID);
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
        // UUID 프론트에서 받아오는 값으로 대체
//        String characterUUID = UUID.randomUUID().toString();

        // 단어장에 캐릭터 이름 저장
        WordsCreateReqDTO wordsCreateReqDTO = new WordsCreateReqDTO(dto.getWorkspaceUUID(), dto.getCharacterName());

        wordsService.createWord(wordsCreateReqDTO, userUUID);


        String groupUUID = dto.getGroupUUID();
        GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
        if (groupUUID != null) {
            checkGroupException(group);
        }

        // 그룹이 조회되지 않을 때 그룹UUID를 null로 변경
//        if (groupRepository.findByGroupUUID(groupUUID) == null) {
//            groupUUID = null;
//        }

        RelationEntity newRelation = RelationEntity.builder()
            .characterUUID(dto.getCharacterUUID())
            .characterName(dto.getCharacterName())
            .relations(dto.getRelations())
            .build();

        relationRepository.save(newRelation);

        CharacterEntityBuilder newCharacter = CharacterEntity.builder()
            .userUUID(userUUID)
            .workspaceUUID(dto.getWorkspaceUUID())
            .groupUUID(groupUUID)
            .characterUUID(dto.getCharacterUUID())
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
            CharacterEntity character = characterRepository.findByCharacterUUID(dto.getCharacterUUID());
            group.addChildCharacter(character);

            groupRepository.save(group);
        }
    }

    @Transactional
    @Override
    public void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto, String userUUID, String workspaceUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        checkCharacterException(character);

        // 단어장 단어 업데이트
        WordsEntity we = wordsRepository.findByUserUUIDAndWorkspaceUUIDAndWord(userUUID, workspaceUUID, dto.getCharacterName());
        System.out.println("단어 수정 UUID: " + we.getWordUUID());
        wordsService.updateWord(we.getWordUUID(), dto.getCharacterName());

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
    public void moveCharacter(String characterUUID, String groupUUID, String userUUID, String workspaceUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        checkCharacterException(character);

        GroupEntity parentGroup = groupRepository.findByGroupUUID(character.getGroupUUID());

        GroupEntity newParentGroup = groupRepository.findByGroupUUID(groupUUID);
        checkGroupException(newParentGroup);

        parentGroup.removeChildCharacter(character);
        newParentGroup.addChildCharacter(character);
        character.moveCharacter(groupUUID);

        characterRepository.save(character);
        groupRepository.save(parentGroup);
        groupRepository.save(newParentGroup);
    }


    @Transactional
    @Override
    public void deleteCharacter(String characterUUID, String userUUID, String workspaceUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);

        // 단어장에서 단어 삭제
        WordsEntity we = wordsRepository.findByUserUUIDAndWorkspaceUUIDAndWord(userUUID,
                workspaceUUID, character.getCharacterName());
        System.out.println("단어 삭제 UUID: " + we.getWordUUID());
        wordsService.deleteWord(we.getWordUUID());

        if (character == null) {
            throw new NoSuchElementFoundException("유효하지 않은 캐릭터 UUID 입니다.");
        }
        if (character.isDeleted()) {
            throw new DeletedElementException("이미 삭제된 캐릭터 입니다.");
        }

        character.deleteCharacter();
        characterRepository.save(character);
        relationRepository.deleteByCharacterUUID(characterUUID);

        GroupEntity parentGroup = groupRepository.findByGroupUUID(character.getGroupUUID());
        if (parentGroup != null) {
            parentGroup.removeChildCharacter(character);
            groupRepository.save(parentGroup);
        }
    }

    @Transactional
    @Override
    public List<CharacterSearchInfoResDTO> searchCharacter(String workspaceUUID, String characterName) {
        List<CharacterEntity> characters = characterRepository
                .findAllByWorkspaceUUIDAndDeletedIsFalseAndCharacterNameLike(workspaceUUID, characterName);
//        System.out.println(characters.size());
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
    public List<RelationDtoRes> getRelationships(String userUUID, String workspaceUUID) {
        List<RelationEntity> allRelation = relationRepository.findAll();
        List<RelationDtoRes> allDto = new ArrayList<>();

        for (RelationEntity relation : allRelation) {

            // 캐릭터(주체,기준) 정보 조회
            String characterUUID = relation.getCharacterUUID();
            String characterName = relation.getCharacterName();
            CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
            if (character == null) {
                continue;
            }

            String characterImage = character.getCharacterImage();
            Map<String, Double> characterNode = null;
            if (character.getCharacterNode() != null) {
                characterNode = new HashMap<>(character.getCharacterNode());
            }

            String groupUUID = character.getGroupUUID();
            String groupName = null;
            Map<String, Double> groupNode = null;
            GroupEntity group = groupRepository.findByGroupUUID(groupUUID);
            if (group != null) {
                groupName = group.getGroupName();
                if (group.getGroupNode() != null) {
                    groupNode = new HashMap<>(group.getGroupNode());
                }
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
                .characterNode(characterNode)
                .groupUUID(groupUUID)
                .groupName(groupName)
                .groupNode(groupNode)
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
    public void moveCharacterNode(String characterUUID, Double x, Double y, String userUUID, String workspaceUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        checkCharacterException(character);

        if (character.getCharacterNode() == null) {
            Map<String, Double> characterNode = new HashMap<>();
            characterNode.put("x", x);
            characterNode.put("y", y);
            character.setCharacterNode(characterNode);
        }
        character.moveCharacterNode(x, y);

        characterRepository.save(character);
    }

    public void checkCharacterException(CharacterEntity character) {
        if (character == null) {
            throw new NoSuchElementFoundException("유효하지 않은 캐릭터 UUID 입니다.");
        }
        if (character.isDeleted()) {
            throw new DeletedElementException("삭제된 캐릭터 입니다.");
        }
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
