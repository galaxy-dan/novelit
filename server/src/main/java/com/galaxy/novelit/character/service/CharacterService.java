package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import org.springframework.stereotype.Service;

public interface CharacterService {
    public CharacterDtoRes getCharacter(String characterUuid);
    public void createCharacter();
}
