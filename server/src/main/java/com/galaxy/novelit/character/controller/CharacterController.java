package com.galaxy.novelit.character.controller;

import com.galaxy.novelit.character.dto.req.CharacterDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterDtoRes;
import com.galaxy.novelit.character.dto.res.DiagramDtoRes;
import com.galaxy.novelit.character.service.CharacterService;
import com.galaxy.novelit.character.service.GroupService;
import com.galaxy.novelit.words.service.WordsService;
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
    public ResponseEntity<Object> getCharacter(@RequestParam String characterUuid) {
        try {
            CharacterDtoRes dto = characterService.getCharacter(characterUuid);;
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createCharacter(@RequestBody CharacterDtoReq dto) {
        try {
            characterService.createCharacter(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateCharacter(@RequestBody CharacterDtoReq dto) {
        try {
//            CharacterDtoRes characterDtoRes = characterService.getCharacter(dto.getCharacterUuid());
            characterService.updateCharacter(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public  ResponseEntity<Object> deleteCharacter(@RequestParam String characterUuid) {
        try {
            characterService.deleteCharacter(characterUuid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/diagram")
    public ResponseEntity<Object> drawDiagram() {
        try {
            final GroupService groupService;
            DiagramDtoRes diagramDtoRes;

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
