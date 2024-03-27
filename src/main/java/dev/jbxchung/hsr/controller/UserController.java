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
    public ResponseEntity getAccountInfo(HttpServletRequest request) {
        // TODO - implement this
        return ResponseEntity.ok("ok");
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

        User savedUser = userDetailsService.addNewUser(newUser);

        return ResponseEntity.ok(new ApiResponse<>(true, savedUser));
    }
}
