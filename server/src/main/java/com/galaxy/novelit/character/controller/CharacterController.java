package com.galaxy.novelit.character.controller;

import com.galaxy.novelit.character.dto.req.CharacterCreateDtoReq;
import com.galaxy.novelit.character.dto.req.CharacterUpdateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.RelationDtoRes;
import com.galaxy.novelit.character.service.CharacterService;
import com.galaxy.novelit.words.service.WordsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public ResponseEntity<Object> getCharacterInfo(@RequestParam String characterUUID) {
        try {
            String userUUID = "temp";
            CharacterDtoRes dto = characterService.getCharacterInfo(characterUUID, userUUID);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCharacter(@RequestBody CharacterCreateDtoReq dto, @RequestParam String userUUID) {
        try {
            characterService.createCharacter(dto, userUUID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateCharacter(@RequestParam String characterUUID, @RequestBody CharacterUpdateDtoReq dto, @RequestParam String userUUID) {
        try {
//            CharacterDtoRes characterDtoRes = characterService.getCharacter(dto.getCharacterUUID());
            characterService.updateCharacter(characterUUID, dto, userUUID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public  ResponseEntity<Object> deleteCharacter(@RequestParam String characterUUID, @RequestParam String userUUID) {
        try {
            characterService.deleteCharacter(characterUUID, userUUID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
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
