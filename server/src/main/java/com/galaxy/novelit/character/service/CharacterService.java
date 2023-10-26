package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.res.CharacterDtoRes;

public interface CharacterService {
    public CharacterDtoRes getCharacter(String characterUuid);
    public void createCharacter();
}
