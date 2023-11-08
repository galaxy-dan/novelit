package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.entity.RelationEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
import com.galaxy.novelit.common.exception.NoSuchElementFoundException;
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
        dto.setRelationship(character.getRelationship());
        dto.setDeleted(character.isDeleted());
        dto.setCharacterImage(character.getCharacterImage());

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getCharacters(String groupUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(groupUUID)
            .orElseThrow(() -> new NoSuchElementFoundException("없는 그룹입니디."));

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
    public List<CharacterSimpleDtoRes> getTopCharacter() {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(null)
            .orElseThrow(() -> new NoSuchElementFoundException("없는 그룹입니디."));
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

        RelationEntity newRelation = RelationEntity.builder()
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .relations(dto.getRelationship().getRelations())
            .build();

        relationRepository.save(newRelation);

        CharacterEntity newCharacter = CharacterEntity.builder()
            .userUUID(userUUID)
            .workspaceUUID(dto.getWorkspaceUUID())
            .groupUUID(dto.getGroupUUID())
            .characterUUID(characterUUID)
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship())
            .characterImage(dto.getCharacterImage())
            .build();

        characterRepository.save(newCharacter);
    }

    @Transactional
    @Override
    public void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto, String userUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);

        CharacterEntity newCharacter = CharacterEntity.builder()
            .userUUID(character.getUserUUID())
            .characterId(character.getCharacterId())
            .characterUUID(characterUUID)
            .groupUUID(dto.getGroupUUID())
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship())
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
    }

//    @Transactional(readOnly = true)
//    @Override
//    public List<RelationDtoRes> getRelationships() {
//
//        return null;
//    }
}
