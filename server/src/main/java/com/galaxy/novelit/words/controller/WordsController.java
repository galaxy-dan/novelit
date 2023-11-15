package com.galaxy.novelit.words.controller;

import com.galaxy.novelit.directory.dto.request.DirectoryNameEditReqDTO;
import com.galaxy.novelit.words.dto.req.WordsCreateReqDTO;
import com.galaxy.novelit.words.dto.req.WordsUpdateReqDTO;
import com.galaxy.novelit.words.dto.res.WordsDtoRes;
import com.galaxy.novelit.words.service.WordsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/words")
@RequiredArgsConstructor
public class WordsController {

    private final WordsService wordsService;
    private final String tempUser = "temp";

    @GetMapping
    public ResponseEntity<Object> getWords(@RequestParam String workspaceUUID) {
        try {
            WordsDtoRes dto = wordsService.getWords(workspaceUUID);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> createWord(@RequestBody WordsCreateReqDTO dto) {
        try {
            wordsService.createWord(dto, tempUser);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> updateWord(@RequestParam String wordUUID, @RequestParam String newWord) {
        try {
            wordsService.updateWord(wordUUID, newWord);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteWord(@RequestParam String wordUUID) {
        try {
            wordsService.deleteWord(wordUUID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
