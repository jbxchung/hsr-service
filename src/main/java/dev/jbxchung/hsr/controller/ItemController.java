package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.UserCreationRequest;
import dev.jbxchung.hsr.entity.Item;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
        Item deletedItem = itemService.deleteItem(itemId);

        return ResponseEntity.ok(new ApiResponse<>(true, deletedItem));
    }
}
