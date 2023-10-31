package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
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
    public CharacterDtoRes getCharacter(String characterUuid) {
        CharacterEntity character = characterRepository.findByCharacterUuid(characterUuid);

//        삭제된 캐릭터 처리
//        if (character.isDeleted()) {
//            return null;
//        }

        CharacterDtoRes dto = new CharacterDtoRes();
        dto.setCharacterName(character.getCharacterName());
        dto.setCharacterUuid(character.getCharacterUuid());
        dto.setInformation(character.getInformation());
        dto.setDescription(character.getDescription());
        dto.setRelationship(character.getRelationship());

        return dto;
    }

    @Transactional
    @Override
    public void createCharacter(CharacterDtoReq dto) {
        String characterUuid = UUID.randomUUID().toString();
//        String characterName = dto.getCharacterName();
//        String description = dto.getDescription();
//        Map<String, String> information = dto.getInformation();
//        Map<String, String> relationship = dto.getRelationship();

        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterUuid(characterUuid)
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship()).build();


//        CharacterEntity.CharacterEntityBuilder builder = CharacterEntity.builder()
//            .characterUuid(characterUuid)
//            .characterName(characterName)
//            .description(description)
//            .information(information)
//            .relationship(relationship);
//        CharacterEntity character = builder.build();

        characterRepository.save(newCharacter);
    }

    @Transactional
    @Override
    public void updateCharacter(CharacterDtoReq dto) {
        CharacterEntity character = characterRepository.findByCharacterUuid(dto.getCharacterUuid());
        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterId(character.getCharacterId())
            .characterName(dto.getCharacterName())
            .description(dto.getDescription())
            .information(dto.getInformation())
            .relationship(dto.getRelationship()).build();

        characterRepository.save(newCharacter);

    }

    @Transactional
    @Override
    public void deleteCharacter(String characterUuid) {
        CharacterEntity character = characterRepository.findByCharacterUuid(characterUuid);
        CharacterEntity newCharacter = CharacterEntity.builder()
            .characterId(character.getCharacterId())
            .isDeleted(true).build();

        characterRepository.save(newCharacter);
    }
}
