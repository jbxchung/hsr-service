package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.entity.Item;
import dev.jbxchung.hsr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getAllItems() {
        List<Item> items = itemService.getAllItems();

        return ResponseEntity.ok(new ApiResponse<>(true, items));
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getItem(@PathVariable String itemId) {
        Item item = itemService.getItem(itemId);

        return ResponseEntity.ok(new ApiResponse<>(true, item));
    }

    // todo - create item DTO
    @PostMapping({"", "/"})
    @PutMapping({"", "/"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createOrUpdateItem(@RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);

        return ResponseEntity.ok(new ApiResponse<>(true, savedItem));
    }

    @DeleteMapping("/{itemId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteItem(@PathVariable String itemId) {
        try {
            Item deletedItem = itemService.deleteItem(itemId);

            return ResponseEntity.ok(new ApiResponse<>(true, deletedItem));
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Cannot delete an item that is in somebody's inventory"), HttpStatus.BAD_REQUEST);
        }
    }
}
