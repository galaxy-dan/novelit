package com.galaxy.novelit.character.service;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSearchInfoResDTO;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterThumbnailDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import java.util.List;

public interface CharacterService {
    CharacterDtoRes getCharacterInfo(String characterUUID, String userUUID);
    List<CharacterThumbnailDtoRes> getCharacters(String groupUUID, String userUUID);
    List<CharacterThumbnailDtoRes> getTopCharacter(String workspaceUUID, String userUUID);
    List<CharacterSimpleDtoRes> getNoGroupCharacters(String workspaceUUID, String userUUID);
    void createCharacter(CharacterCreateDtoReq dto, String userUUID);
    void updateCharacter(String characterUUID, CharacterUpdateDtoReq dto, String userUUID);
    void deleteCharacter(String characterUUID, String userUUID);

    List<CharacterSearchInfoResDTO> searchCharacter(String workspaceUUID, String characterName);
    List<RelationDtoRes> getRelationships();
    void moveCharacter(String characterUUID, String groupUUID, String userUUID);
    List<CharacterSimpleDtoRes> getAllCharacters(String workspaceUUID, String userUUID);
    void moveCharacterNode(String characterUUID, Double x, Double y, String userUUID);
}
