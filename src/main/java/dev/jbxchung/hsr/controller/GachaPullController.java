package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.GachaPullRequest;
import dev.jbxchung.hsr.entity.*;
import dev.jbxchung.hsr.service.GachaPullService;
import dev.jbxchung.hsr.service.UserDetailsServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pulls")
public class GachaPullController {

    @Autowired
    private GachaPullService gachaPullService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getAll(HttpServletRequest request) {
        String callerName = request.getRemoteUser();

        try {
            User caller = userDetailsService.getUser(callerName);

            List<GachaPull> pulls = gachaPullService.getPullHistory(caller);
            return ResponseEntity.ok(new ApiResponse<>(true, pulls));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> recordPull(@RequestBody GachaPullRequest gachaPullRequest, HttpServletRequest request) {
        String callerName = request.getRemoteUser();

        if (gachaPullRequest.getEntityType() == null) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Unknown entity type"), HttpStatus.BAD_REQUEST);
        }

        try {
            User caller = userDetailsService.getUser(callerName);
            GachaPull pull = gachaPullService.recordPull(caller, gachaPullRequest);

            return ResponseEntity.ok(new ApiResponse<>(true, pull));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "Entity not found"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
