package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Character;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CharacterService {
    List<Character> getAll();
    Character get(String characterName) throws EntityNotFoundException;

    InputStreamResource getThumbnail(String characterName) throws EntityNotFoundException, IOException;

    Character save(Character character);
    String saveThumbnail(MultipartFile thumbnail) throws IOException;
    Character delete(String characterName) throws EntityNotFoundException;
}
