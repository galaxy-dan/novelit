package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSearchInfoResDTO;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes.RelationDto;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.RelationEntity;
import com.galaxy.novelit.character.entity.RelationEntity.Relation;
import com.galaxy.novelit.character.repository.CharacterRepository;
import com.galaxy.novelit.character.repository.GroupRepository;
import com.galaxy.novelit.character.repository.RelationRepository;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
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

//        characterUUID가 db에 없을 때
//        if (character == null) {
//            throw new CharacterNotFoundException("Character not found for UUID: " + characterUUID);
//        }

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
    public List<CharacterSimpleDtoRes> getCharacters(String groupUUID, String userUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(groupUUID);
//            .orElseThrow(() -> new NoSuchElementFoundException("없는 그룹입니디."));

        List<CharacterSimpleDtoRes> characterSimpleInfoList = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterSimpleDtoRes characterSimpleDtoRes = CharacterSimpleDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterImage(character.getCharacterImage())
                .characterName(character.getCharacterName())
                .information(new ArrayList<>(character.getInformation().subList(0, 2)))
                .build();
            characterSimpleInfoList.add(characterSimpleDtoRes);
        }

        return characterSimpleInfoList;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getTopCharacter(String workspaceUUID, String userUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByWorkspaceUUIDAndGroupUUIDIsNull(workspaceUUID);

//        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(null);
//            .orElseThrow(() -> new NoSuchElementFoundException("없는 그룹입니디."));
        List<CharacterSimpleDtoRes> dto = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterSimpleDtoRes characterSimpleDtoRes = CharacterSimpleDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterName(character.getCharacterName())
                .information(character.getInformation())
                .characterImage(character.getCharacterImage())
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
        if (groupRepository.findByGroupUUID(groupUUID) == null) {
            groupUUID = null;
        }


        RelationEntity newRelation = RelationEntity.builder()
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .relations(dto.getRelations())
            .build();

        relationRepository.save(newRelation);

        CharacterEntity newCharacter = CharacterEntity.builder()
            .userUUID(userUUID)
            .workspaceUUID(dto.getWorkspaceUUID())
            .groupUUID(groupUUID)
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(newRelation)
            .characterImage(dto.getCharacterImage())
            .build();

        characterRepository.save(newCharacter);
    }

    @Transactional
    @Override
    public void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        RelationEntity relation = relationRepository.findByCharacterUUID(characterUUID);
        RelationEntity newRelation;
        CharacterEntity newCharacter;

        newRelation = RelationEntity.builder()
            .id(relation.getId())
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .relations(dto.getRelations())
            .build();

        relationRepository.save(newRelation);

        newCharacter = CharacterEntity.builder()
            .userUUID(userUUID)
            .characterId(character.getCharacterId())
            .characterUUID(characterUUID)
            .groupUUID(dto.getGroupUUID())
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(newRelation)
            .characterImage(dto.getCharacterImage())
            .isDeleted(character.isDeleted())
            .build();

        characterRepository.save(newCharacter);
    }

    @Transactional
    @Override
    public void deleteCharacter(String characterUUID, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        character.deleteCharacter();
        characterRepository.save(character);
        relationRepository.deleteByCharacterUUID(characterUUID);
    }

    @Transactional
    @Override
    public List<CharacterSearchInfoResDTO> searchCharacter(String characterName) {
        List<CharacterEntity> characters = characterRepository.findAllByCharacterName(characterName);
        List<CharacterSearchInfoResDTO> characterInfoList = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterSearchInfoResDTO characterSearchInfoResDTO = CharacterSearchInfoResDTO.builder()
                .characterUUID(character.getCharacterUUID())
                .characterImage(character.getCharacterImage())
                .groupUUID(character.getGroupUUID())
                .groupName(groupRepository.findByGroupUUID(character.getGroupUUID()).getGroupName())
                .characterName(character.getCharacterName())
                .information(new ArrayList<>(character.getInformation().subList(0, 2)))
                .build();
            characterInfoList.add(characterSearchInfoResDTO);
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
            String groupUUID = characterRepository.findByCharacterUUID(characterUUID).getGroupUUID();
            String groupName;
            if (groupRepository.findByGroupUUID(groupUUID) == null) {
                groupName = null;
            } else {
                groupName = groupRepository.findByGroupUUID(groupUUID).getGroupName();
            }

            // 타켓 정보 조회
            List<RelationDto> targetList = new ArrayList<>();

            for (Relation target : relation.getRelations()) {
                String targetUUID = target.getTargetUUID();
                String targetName = target.getTargetName();
                String targetGroupUUID;
                String targetGroupName;
                if (characterRepository.findByCharacterUUID(targetUUID) == null) {
                    targetGroupUUID = null;
                } else {
                    targetGroupUUID = characterRepository.findByCharacterUUID(targetUUID).getGroupUUID();
                }
                if (groupRepository.findByGroupUUID(targetGroupUUID) == null) {
                    targetGroupName = null;
                } else {
                    targetGroupName = groupRepository.findByGroupUUID(targetGroupUUID).getGroupName();
                }
                String content = target.getContent();

                RelationDto targetDto = RelationDto.builder()
                    .targetUUID(targetUUID)
                    .targetName(targetName)
                    .targetGroupUUID(targetGroupUUID)
                    .targetGroupName(targetGroupName)
                    .content(content)
                    .build();

                targetList.add(targetDto);
            }

            // 조회한 정보 모두 dto에 저장
            RelationDtoRes dto = RelationDtoRes.builder()
                .characterUUID(characterUUID)
                .characterName(characterName)
                .groupUUID(groupUUID)
                .groupName(groupName)
                .relations(targetList)
                .build();

            allDto.add(dto);
        }

        return allDto;
    }
}
