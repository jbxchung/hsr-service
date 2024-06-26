package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.repository.CharacterRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Service
public class CharacterService implements GachaEntityService<Character> {
    Logger logger = LoggerFactory.getLogger(CharacterService.class);

    @Value("${hsr.resource.path}")
    String resourcePath;

    @Value("${hsr.resource.path.override:}")
    String resourcePathOverride;

    @Autowired
    private CharacterRepository characterRepository;

    @PostConstruct
    public void init() {
        if (!resourcePathOverride.isEmpty()) {
            resourcePath = resourcePathOverride;
        }
    }

    @Override
    public List<Character> getAll() {
        return characterRepository.findAll();
    }

    @Override
    public Character getById(String characterId) throws EntityNotFoundException {
        return characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find character with id " + characterId));
    }

    @Override
    public InputStreamResource getThumbnail(String characterId) throws EntityNotFoundException, IOException {
        Character character = this.getById(characterId);
        String thumbnailPath = character.getThumbnailFilePath();

        try {
            return new InputStreamResource(Files.newInputStream(Path.of(thumbnailPath)));
        } catch (FileNotFoundException ex) {
            logger.info("File not found");
        } catch(AccessDeniedException e) {
            logger.info("File access denied");
        }

        return null;
    }

    @Override
    public Character save(Character character) {
        logger.info("Saving character {}", character.getId());
        return characterRepository.save(character);
    }

    @Override
    public String saveThumbnail(MultipartFile thumbnail) throws IOException {
        String destinationFileLocation = resourcePath + "/characters/" + thumbnail.getOriginalFilename();

        Path destinationFilePath = Path.of(destinationFileLocation);
        Files.createDirectories(destinationFilePath.getParent());

        thumbnail.transferTo(destinationFilePath);
        logger.info("Saved character thumbnail to {}", destinationFilePath);

        return destinationFileLocation;
    }

    @Override
    public Character delete(String characterId) throws EntityNotFoundException {
        Character characterToDelete = characterRepository.findById(characterId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find character with id " + characterId));

        // todo - make sure this character object's lazy fields (if any) are fully loaded so the return value can be deserialized
        characterRepository.delete(characterToDelete);

        return characterToDelete;
    }
}
