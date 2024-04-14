package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.GachaEntityCreationRequest;
import dev.jbxchung.hsr.dto.LightConeCreationRequest;
import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.entity.LightCone;
import dev.jbxchung.hsr.service.LightConeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/lightcone")
public class LightConeController implements GachaEntityController<LightCone, LightConeCreationRequest> {
    Logger logger = LoggerFactory.getLogger(LightConeController.class);
    @Autowired
    LightConeService lightConeService;

    @Override
    public ResponseEntity<ApiResponse<List<LightCone>>> getAll() {
        List<LightCone> lightCones = lightConeService.getAll();

        ApiResponse<List<LightCone>> response = new ApiResponse<>(true, lightCones);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ApiResponse<LightCone>> get(String id) {
        LightCone lightCone = lightConeService.getById(id);

        ApiResponse<LightCone> response = new ApiResponse<>(true, lightCone);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<InputStreamResource> getThumbnail(String id) throws IOException {
        InputStreamResource thumbnail = lightConeService.getThumbnail(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("image/webp"))
                .body(thumbnail);
    }

    @Override
    public ResponseEntity<?> save(LightConeCreationRequest lcCreationRequest) {
        LightCone newLightCone = LightCone.builder()
                .id(lcCreationRequest.getId())
                .name(lcCreationRequest.getName())
                .rarity(lcCreationRequest.getRarity())
                .path(lcCreationRequest.getPath())
                .description(lcCreationRequest.getDescription())
                .build();

        // todo - handle null thumbnail
        String thumbnailPath;
        // save thumbnail at configured path
        try {
            thumbnailPath = lightConeService.saveThumbnail(lcCreationRequest.getThumbnail());
        } catch (Exception e) {
            logger.error("Failed to save thumbnail", e);
            return ResponseEntity.internalServerError().body("Failed to save thumbnail");
        }

        newLightCone.setThumbnailFilePath(thumbnailPath);
        LightCone savedLightCone = lightConeService.save(newLightCone);

        ApiResponse<?> response = new ApiResponse<>(true, savedLightCone);
        return ResponseEntity.ok(response);
    }
}
