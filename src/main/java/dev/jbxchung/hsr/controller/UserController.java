package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.UserCreationRequest;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/info")
    public ResponseEntity<?> getAccountInfo(HttpServletRequest request) {
        String caller = request.getRemoteUser();
        User user = userDetailsService.getUser(caller);

        return ResponseEntity.ok(new ApiResponse<>(true, user));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userDetailsService.getAllUsers();

        return ResponseEntity.ok(new ApiResponse<>(true, users));
    }

    @PostMapping({"", "/"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createNewUser(@RequestBody UserCreationRequest newUserRequest) {
        // todo - implement builder pattern
        User newUser = new User();
        newUser.setAccountName(newUserRequest.getUsername());
        newUser.setEmail(newUserRequest.getEmail());
        newUser.setEncryptedPassword(encoder.encode(newUserRequest.getPassword()));

        newUser.setCreated(new Date());
        newUser.setRole(newUserRequest.getRole() != null ? newUserRequest.getRole() : User.Role.USER);

        User savedUser = userDetailsService.saveUser(newUser);

        return ResponseEntity.ok(new ApiResponse<>(true, savedUser));
    }

    @PutMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUser(@PathVariable String username, @RequestBody UserCreationRequest newUserRequest) {
        User user = userDetailsService.getUser(username);

        if (newUserRequest.getUsername() != null) {
            user.setAccountName(newUserRequest.getUsername());
        }
        if (newUserRequest.getRole() != null) {
            user.setRole(newUserRequest.getRole());
        }
        if (newUserRequest.getEmail() != null) {
            user.setEmail(newUserRequest.getEmail());
        }

        User savedUser = userDetailsService.saveUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, savedUser));
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        User deletedUser = userDetailsService.deleteUser(username);

        return ResponseEntity.ok(new ApiResponse<>(true, deletedUser));
    }
}
