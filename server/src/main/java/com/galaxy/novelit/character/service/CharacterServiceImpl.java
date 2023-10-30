package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.entity.CharacterEntity;
import com.galaxy.novelit.character.repository.CharacterRepository;
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
        CharacterEntity characterEntity = characterRepository.findByCharacterUuid(characterUuid);

        return new CharacterDtoRes();
    }

    @Transactional
    @Override
    public void createCharacter() {

    }

    @Transactional
    @Override
    public void updateCharacter(CharacterDtoReq characterDtoReq) {

    }

    @Transactional
    @Override
    public void deleteCharacter(String characterUuid) {

    }
}
