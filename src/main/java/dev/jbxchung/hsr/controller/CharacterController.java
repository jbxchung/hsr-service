package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.CharacterCreationRequest;
import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.service.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {
    Logger logger = LoggerFactory.getLogger(CharacterController.class);

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

    @GetMapping("/{name}/thumbnail")
    public ResponseEntity<?> getThumbnail(@PathVariable String name) throws IOException {
        InputStreamResource thumbnail = characterService.getThumbnail(name);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(thumbnail);
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
            logger.error("Failed to save thumbnail", e);
            return ResponseEntity.internalServerError().body("Failed to save thumbnail");
        }

        newCharacter.setThumbnailFilePath(thumbnailPath);
        Character savedCharacter = characterService.save(newCharacter);

        ApiResponse<?> response = new ApiResponse<>(true, savedCharacter);
        return ResponseEntity.ok(response);
    }
}
