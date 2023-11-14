package com.galaxy.novelit.character.controller;


import com.galaxy.novelit.character.dto.req.GroupCreateDtoReq;
import com.galaxy.novelit.character.dto.res.AllGroupsCharactersDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterSimpleDtoRes;
import com.galaxy.novelit.character.dto.res.CharacterThumbnailDtoRes;
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
    private final String tempUser = "temp";

    @GetMapping
    public ResponseEntity<Object> getGroupInfo(@RequestParam String workspaceUUID, @RequestParam String groupUUID, Authentication authentication) {
//        GroupDtoRes dto = groupService.getGroupInfo(groupUUID, authentication.getName(), workspaceUUID);
        GroupDtoRes dto = groupService.getGroupInfo(groupUUID, tempUser, workspaceUUID);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/characters")
    public ResponseEntity<Object> getSimpleCharactersInfo(@RequestParam String workspaceUUID, @RequestParam String groupUUID, Authentication authentication) {
//        List<CharacterSimpleDtoRes> dto = characterService.getCharacters(groupUUID, authentication.getName(), workspaceUUID);
        List<CharacterThumbnailDtoRes> dto = characterService.getCharacters(groupUUID, tempUser, workspaceUUID);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("/top")
    public ResponseEntity<Object> getTopGroupAndCharacter(@RequestParam String workspaceUUID, Authentication authentication) {
//        List<GroupSimpleDtoRes> groupDto = groupService.getTopGroup(authentication.getName());
//        List<CharacterSimpleDtoRes> characterDto = characterService.getTopCharacter(authentication.getName());

        List<GroupSimpleDtoRes> groupDto = groupService.getTopGroup(workspaceUUID, tempUser);
        List<CharacterThumbnailDtoRes> characterDto = characterService.getTopCharacter(workspaceUUID, tempUser);

        Map<String, Object> response = new HashMap<>();
        response.put("groups", groupDto);
        response.put("characters", characterDto);

        return ResponseEntity.ok(response);
    }


    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody GroupCreateDtoReq dto, Authentication authentication) {
//        groupService.createGroup(dto, authentication.getName());
        groupService.createGroup(dto, tempUser);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<Object> updateGroupName(@RequestParam String workspaceUUID, @RequestParam String groupUUID, @RequestParam String newName, Authentication authentication) {
//        groupService.updateGroupName(groupUUID, newName, authentication.getName(), workspaceUUID);
        groupService.updateGroupName(groupUUID, newName, tempUser, workspaceUUID);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteGroup(@RequestParam String workspaceUUID, @RequestParam String groupUUID, Authentication authentication) {
//        groupService.deleteGroup(groupUUID, authentication.getName(),workspaceUUID);
        groupService.deleteGroup(groupUUID, tempUser,workspaceUUID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/character/all")
    public ResponseEntity<Object> getGroupAndCharatcter(@RequestParam String workspaceUUID, Authentication authentication) {
//        List<AllGroupsCharactersDtoRes> dto = groupService.getAllGroupsAndCharacters(workspaceUUID, authentication.getName());
        List<AllGroupsCharactersDtoRes> dto = groupService.getAllGroupsAndCharacters(workspaceUUID, tempUser);
        List<CharacterSimpleDtoRes> noGroupDto = characterService.getNoGroupCharacters(workspaceUUID, tempUser);

        Map<String, Object> response = new HashMap<>();
        response.put("allGroupsAndCharacters", dto);
        response.put("noGroupCharacters", noGroupDto);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/node")
    public ResponseEntity<Object> moveGroupNode(@RequestParam String workspaceUUID, @RequestParam String groupUUID, @RequestParam Double x, @RequestParam Double y, Authentication authentication) {
//        groupService.moveGroupNode(groupUUID, x, y, authentication.getName(), workspaceUUID);
        groupService.moveGroupNode(groupUUID, x, y, tempUser, workspaceUUID);

        return ResponseEntity.ok().build();
    }

}
