package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.CharacterCreationRequest;
import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCharacters() {
        List<Character> characters = characterService.getAll();

        ApiResponse<?> response = new ApiResponse<>(true, characters);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> getCharacter(@PathVariable String name) {
        Character character = characterService.get(name);

        ApiResponse<?> response = new ApiResponse<>(true, character);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = {"", "/"}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveCharacter(@ModelAttribute CharacterCreationRequest newCharacterRequest) {
        // todo - implement builder pattern
        Character newCharacter = new Character();
        newCharacter.setId(newCharacterRequest.getId());
        newCharacter.setName(newCharacterRequest.getName());
        newCharacter.setRarity(newCharacterRequest.getRarity());
        newCharacter.setPath(newCharacterRequest.getPath());
        newCharacter.setElement(newCharacterRequest.getElement());
        newCharacter.setDescription(newCharacterRequest.getDescription());

        // todo - handle null thumbnail
        String thumbnailPath = "";
        // save thumbnail at configured path
        try {
            thumbnailPath = characterService.saveThumbnail(newCharacterRequest.getThumbnail());
        } catch (Exception e) {
            // todo - handle failed to save thumbnail
        }

        newCharacter.setThumbnailFilePath(thumbnailPath);
        Character savedCharacter = characterService.save(newCharacter);

        ApiResponse<?> response = new ApiResponse<>(true, savedCharacter);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/thumbnail")
    public ResponseEntity<?> addThumbnail(@RequestPart File thumbnail) {
        return ResponseEntity.ok("not implemented");
    }
}
