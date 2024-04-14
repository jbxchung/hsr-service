package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.entity.GachaEntity;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface GachaEntityService<T extends GachaEntity> {
    List<T> getAll();
    T getById(String entityId);
    InputStreamResource getThumbnail(String entityId) throws EntityNotFoundException, IOException;

    T save(T entity);
    String saveThumbnail(MultipartFile thumbnail) throws IOException;
    T delete(String entityId) throws EntityNotFoundException;
}
