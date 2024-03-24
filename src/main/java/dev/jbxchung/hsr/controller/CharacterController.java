package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/character")
public class CharacterController {

    @Autowired
    private CharacterService characterService;
//.requestMatchers("/character/**").hasRole("ADMIN")

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

    @PostMapping("/")
    public ResponseEntity<?> saveCharacter(@RequestBody Character character) {
        return ResponseEntity.ok("not implemented");
    }

    @PostMapping("/thumbnail")
    public ResponseEntity<?> addThumbnail(@RequestPart File thumbnail) {
        return ResponseEntity.ok("not implemented");
    }
}
