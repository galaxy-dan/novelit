package com.galaxy.novelit.character.controller;


import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.GroupDtoRes;
import com.galaxy.novelit.character.dto.res.GroupSimpleDtoRes;
import com.galaxy.novelit.character.service.CharacterService;
import com.galaxy.novelit.character.service.GroupService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
    private final CharacterService characterService;

    @GetMapping
    public ResponseEntity<Object> getGroupInfo(@RequestParam String groupUUID, Authentication authentication) {
        try {
            GroupDtoRes dto = groupService.getGroupInfo(groupUUID, authentication.getName());
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> getSimpleCharactersInfo(@RequestParam String groupUUID, Authentication authentication) {
        List<CharacterSimpleDtoRes> dto = characterService.getCharacters(groupUUID, authentication.getName());
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/top")
    public ResponseEntity<Object> topGroupInfo(Authentication authentication) {
        try {
            List<GroupSimpleDtoRes> groupDto = groupService.getTopGroup(authentication.getName());
            List<CharacterSimpleDtoRes> characterDto = characterService.getTopCharacter(authentication.getName());

            Map<String, Object> response = new HashMap<>();
            response.put("groups", groupDto);
            response.put("characters", characterDto);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody GroupCreateDtoReq dto, Authentication authentication) {
        try {
            groupService.createGroup(dto, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PatchMapping
    public ResponseEntity<Object> updateGroupName(@RequestParam String groupUUID, @RequestParam String newName, Authentication authentication) {
        try {
            groupService.updateGroupName(groupUUID, newName, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteGroup(@RequestParam String groupUUID, Authentication authentication) {
        try {
            groupService.deleteGroup(groupUUID, authentication.getName());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
