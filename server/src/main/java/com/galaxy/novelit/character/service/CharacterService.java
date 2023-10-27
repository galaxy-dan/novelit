package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.res.CharacterDtoRes;

public interface CharacterService {
    CharacterDtoRes getCharacter(String characterUuid);
    void createCharacter();
}
