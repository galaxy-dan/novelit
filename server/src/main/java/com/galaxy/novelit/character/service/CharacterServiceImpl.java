package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final MongoTemplate mongoTemplate;

    @Transactional(readOnly = true)
    @Override
    public CharacterDtoRes getCharacterInfo(String characterUUID) {
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
        dto.setCharacterImage(character.getCharacterImage());
        dto.setDeleted(character.isDeleted());

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getCharacters(String groupUUID) {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(groupUUID);
        List<CharacterSimpleDtoRes> dto = new ArrayList<>();

        for (CharacterEntity character : characters) {
            CharacterSimpleDtoRes characterSimpleDtoRes = CharacterSimpleDtoRes.builder()
                .characterUUID(character.getCharacterUUID())
                .characterName(character.getCharacterName())
                .information(character.getInformation())
                .characterImage(character.getCharacterImage()).build();
            dto.add(characterSimpleDtoRes);
        }

        return dto;
    }

    @Transactional(readOnly = true)
    @Override
    public List<CharacterSimpleDtoRes> getTopCharacter() {
        List<CharacterEntity> characters = characterRepository.findAllByGroupUUID(null);
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
    public void createCharacter(CharacterCreateDtoReq dto) {
        String characterUUID = UUID.randomUUID().toString();

        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterUUID(characterUUID)
            .groupUUID(dto.getGroupUUID())
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship()).build();

        characterRepository.save(newCharacter);
    }

    @Transactional
    @Override
    public void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto) {
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
    public void deleteCharacter(String characterUUID) {
        CharacterEntity character = characterRepository.findByCharacterUUID(characterUUID);
        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterId(character.getCharacterId())
            .userUUID(character.getUserUUID())
            .groupUUID(character.getGroupUUID())
            .characterUUID(character.getCharacterUUID())
            .characterName(character.getCharacterName())
            .description(character.getDescription())
            .information(character.getInformation())
            .relationship(character.getRelationship())
            .characterImage(character.getCharacterImage())
            .isDeleted(true).build();

        characterRepository.save(newCharacter);
    }
}
