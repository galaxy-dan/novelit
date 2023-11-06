package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import java.util.List;

public interface CharacterService {
    CharacterDtoRes getCharacterInfo(String characterUUID);
    List<CharacterSimpleDtoRes> getCharacters(String groupUUID);
    List<CharacterSimpleDtoRes> getTopCharacter();
    void createCharacter(CharacterCreateDtoReq dto);
    void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto);
    void deleteCharacter(String characterUUID);
    List<RelationDtoRes> getRelationships();
}
