package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.entity.LightCone;
import dev.jbxchung.hsr.repository.LightConeRepository;
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
public class LightConeService implements GachaEntityService<LightCone> {
    Logger logger = LoggerFactory.getLogger(LightConeService.class);

    @Value("${hsr.resource.path}")
    String resourcePath = null;

    @Value("${hsr.resource.path.override:}")
    String resourcePathOverride = null;

    @Autowired
    LightConeRepository lightConeRepository;

    @PostConstruct
    public void init() {
        if (!resourcePathOverride.isEmpty()) {
            resourcePath = resourcePathOverride;
        }
    }

    @Override
    public List<LightCone> getAll() {
        return lightConeRepository.findAll();
    }

    @Override
    public LightCone getById(String lightConeId) {
        return lightConeRepository.findById(lightConeId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find light cone with id " + lightConeId));
    }

    @Override
    public InputStreamResource getThumbnail(String lightConeId) throws EntityNotFoundException, IOException {
        LightCone lightCone = this.getById(lightConeId);
        String thumbnailPath = lightCone.getThumbnailFilePath();

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
    public LightCone save(LightCone lightCone) {
        logger.info("Saving light cone {}", lightCone.getId());
        return lightConeRepository.save(lightCone);
    }

    @Override
    public String saveThumbnail(MultipartFile thumbnail) throws IOException {
        String destinationFileLocation = resourcePath + "/lightcones/" + thumbnail.getOriginalFilename();

        Path destinationFilePath = Path.of(destinationFileLocation);
        Files.createDirectories(destinationFilePath.getParent());

        thumbnail.transferTo(destinationFilePath);
        logger.info("Saved light cone thumbnail to {}", destinationFilePath);

        return destinationFileLocation;
    }

    @Override
    public LightCone delete(String lightConeId) throws EntityNotFoundException {
        LightCone lcToDelete = lightConeRepository.findById(lightConeId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find light cone with id " + lightConeId));

        // todo - make sure this object's lazy fields (if any) are fully loaded so the return value can be deserialized
        lightConeRepository.delete(lcToDelete);

        return lcToDelete;
    }
}
