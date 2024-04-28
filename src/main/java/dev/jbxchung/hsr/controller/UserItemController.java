package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.UserItemDTO;
import dev.jbxchung.hsr.entity.Item;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.entity.UserItem;
import dev.jbxchung.hsr.service.ItemService;
import dev.jbxchung.hsr.service.UserDetailsServiceImpl;
import dev.jbxchung.hsr.service.UserItemService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class UserItemController {
    @Autowired
    private UserItemService userItemService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getInventory(HttpServletRequest request) {
        String callerName = request.getRemoteUser();

        try {
            User caller = userDetailsService.getUser(callerName);

            List<UserItem> inventory = userItemService.getInventory(caller);

            return ResponseEntity.ok(new ApiResponse<>(true, inventory));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> addItemToInventory(@RequestBody UserItemDTO userItemDTO, HttpServletRequest request) {
        String callerName = request.getRemoteUser();

        try {
            User caller = userDetailsService.getUser(callerName);

            Item item = itemService.getItem(userItemDTO.getItemId());
            UserItem userItem = userItemService.addUserItem(item, caller, userItemDTO.getQuantity());

            return ResponseEntity.ok(new ApiResponse<>(true, userItem));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping({"", "/"})
    public ResponseEntity<?> deleteItemFromInventory(@RequestBody UserItemDTO userItemDTO, HttpServletRequest request) {
        String callerName = request.getRemoteUser();

        try {
            User caller = userDetailsService.getUser(callerName);

            Item item = itemService.getItem(userItemDTO.getItemId());
            UserItem userItem = userItemService.removeUserItem(item, caller, userItemDTO.getQuantity());

            return ResponseEntity.ok(new ApiResponse<>(true, userItem));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.UNAUTHORIZED);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, e.getMessage()), HttpStatus.NOT_FOUND);
        }
    }
}
