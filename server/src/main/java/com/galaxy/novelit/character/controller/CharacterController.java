package com.galaxy.novelit.character.controller;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSearchInfoResDTO;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
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
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Object> getCharacterInfo(@RequestParam String characterUUID, Authentication authentication) {
        try {
//            CharacterDtoRes dto = characterService.getCharacterInfo(characterUUID, authentication.getName());
            CharacterDtoRes dto = characterService.getCharacterInfo(characterUUID, tempUser);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCharacter(@RequestBody CharacterCreateDtoReq dto, Authentication authentication) {
        try {
//            characterService.createCharacter(dto, authentication.getName());
            characterService.createCharacter(dto, tempUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> updateCharacter(@RequestParam String characterUUID, @RequestBody CharacterUpdateDtoReq dto, Authentication authentication) {
        try {
//            characterService.updateCharacter(characterUUID, dto, authentication.getName());
            characterService.updateCharacter(characterUUID, dto, tempUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public  ResponseEntity<Object> deleteCharacter(@RequestParam String characterUUID, Authentication authentication) {
        try {
//            characterService.deleteCharacter(characterUUID, authentication.getName());
            characterService.deleteCharacter(characterUUID, tempUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/diagram")
    public ResponseEntity<Object> getRelationships(Authentication authentication) {
        try {

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Object> searchCharacter(@RequestParam String characterName) {
        List<CharacterSearchInfoResDTO> charactersList = characterService.searchCharacter(characterName);

        return ResponseEntity.ok().body("");
    }

//    @GetMapping("/diagram")
//    public ResponseEntity<Object> getRelationships() {
//        try {
////            List<RelationDtoRes> dto = characterService.getRelationships();
//
//            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//        }
//    }

}
