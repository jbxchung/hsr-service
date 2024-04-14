package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.CharacterDTO;
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
public class CharacterController implements GachaEntityController<Character, CharacterDTO> {
    Logger logger = LoggerFactory.getLogger(CharacterController.class);

    @Autowired
    private CharacterService characterService;

    @Override
    public ResponseEntity<ApiResponse<List<Character>>> getAll() {
        List<Character> characters = characterService.getAll();

        ApiResponse<List<Character>> response = new ApiResponse<>(true, characters);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<Character>> get(String id) {
        Character character = characterService.getById(id);

        ApiResponse<Character> response = new ApiResponse<>(true, character);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InputStreamResource> getThumbnail(@PathVariable String id) throws IOException {
        InputStreamResource thumbnail = characterService.getThumbnail(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(thumbnail);
    }

    @PostMapping(value = {"", "/"}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@ModelAttribute CharacterDTO characterDTO) {
        Character newCharacter = Character.builder()
                .id(characterDTO.getId())
                .name(characterDTO.getName())
                .rarity(characterDTO.getRarity())
                .path(characterDTO.getPath())
                .element(characterDTO.getElement())
                .description(characterDTO.getDescription())
                .build();

        // todo - handle null thumbnail
        String thumbnailPath;
        // save thumbnail at configured path
        try {
            thumbnailPath = characterService.saveThumbnail(characterDTO.getThumbnail());
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
