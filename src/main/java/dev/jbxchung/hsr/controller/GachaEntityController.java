package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

public interface GachaEntityController<T, DTO> {
    @GetMapping("/all")
    ResponseEntity<ApiResponse<List<T>>> getAll();

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<T>> get(@PathVariable String id);

    @GetMapping("/{id}/thumbnail")
    ResponseEntity<InputStreamResource> getThumbnail(@PathVariable String id) throws IOException;

    @PostMapping(value = {"", "/"}, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> save(@ModelAttribute DTO dto);

    @DeleteMapping("/{id}")
    @PreAuthorize(("hasRole('ADMIN')"))
    ResponseEntity<?> delete(@PathVariable String id);
}
