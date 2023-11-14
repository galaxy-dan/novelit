package com.galaxy.novelit.character.controller;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSearchInfoResDTO;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import com.galaxy.novelit.character.service.CharacterService;
import com.galaxy.novelit.words.service.WordsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {
    private final CharacterService characterService;
    private final WordsService wordsService;
    private final String tempUser = "temp";

    @GetMapping
    public ResponseEntity<Object> getCharacterInfo(@RequestParam String workspaceUUID, @RequestParam String characterUUID, Authentication authentication) {
//        CharacterDtoRes dto = characterService.getCharacterInfo(characterUUID, authentication.getName(), String workspaceUUID);
        CharacterDtoRes dto = characterService.getCharacterInfo(characterUUID, tempUser, workspaceUUID);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Object> createCharacter(@RequestBody CharacterCreateDtoReq dto, Authentication authentication) {
//        characterService.createCharacter(dto, authentication.getName());
        characterService.createCharacter(dto, tempUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Object> updateCharacter(@RequestParam String workspaceUUID, @RequestParam String characterUUID, @RequestBody CharacterUpdateDtoReq dto, Authentication authentication) {
//        characterService.updateCharacter(characterUUID, dto, authentication.getName(), workspaceUUID);
        characterService.updateCharacter(characterUUID, dto, tempUser, workspaceUUID);
        return ResponseEntity.ok().build();

    }

    @DeleteMapping
    public  ResponseEntity<Object> deleteCharacter(@RequestParam String workspaceUUID, @RequestParam String characterUUID, Authentication authentication) {
//        characterService.deleteCharacter(characterUUID, authentication.getName(), workspaceUUID);
        characterService.deleteCharacter(characterUUID, tempUser, workspaceUUID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchCharacter(@RequestParam String workspaceUUID, @RequestParam String characterName) {
        List<CharacterSearchInfoResDTO> charactersList = characterService.searchCharacter(workspaceUUID, characterName);

        return ResponseEntity.ok().body(charactersList);
    }

    @GetMapping("/diagram")
    public ResponseEntity<Object> getRelationships(@RequestParam String workspaceUUID, Authentication authentication) {
//        List<RelationDtoRes> dto = characterService.getRelationships(authentication.getName(), workspaceUUID);
        List<RelationDtoRes> dto = characterService.getRelationships(tempUser, workspaceUUID);

        return ResponseEntity.ok(dto);
    }

    @PatchMapping("/move")
    public ResponseEntity<Object> moveCharacter(@RequestParam String workspaceUUID, @RequestParam String characterUUID, @RequestParam String groupUUID, Authentication authentication) {
//        characterService.moveCharacter(characterUUID, groupUUID, authentication.getName(), workspaceUUID);
        characterService.moveCharacter(characterUUID, groupUUID, tempUser, workspaceUUID);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/node")
    public ResponseEntity<Object> moveCharacterNode(@RequestParam String workspaceUUID, @RequestParam String characterUUID, @RequestParam Double x, @RequestParam Double y, Authentication authentication) {
//        characterService.moveCharacterNode(characterUUID, x, y, authentication.getName(), workspaceUUID);
        characterService.moveCharacterNode(characterUUID, x, y, tempUser, workspaceUUID);

        return ResponseEntity.ok().build();
    }
}
