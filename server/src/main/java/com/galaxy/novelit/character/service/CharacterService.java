package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import java.util.List;

public interface CharacterService {
    CharacterDtoRes getCharacterInfo(String characterUUID);
    List<CharacterSimpleDtoRes> getCharacters(String groupUUID);
    List<CharacterSimpleDtoRes> getTopCharacter();
    void createCharacter(CharacterCreateDtoReq dto);
    void updateCharacter(CharacterDtoReq dto);
    void deleteCharacter(String characterUUID);
}
