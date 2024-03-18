package dev.jbxchung.hsr.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @GetMapping("/info")
    public ResponseEntity getAccountInfo(HttpServletRequest request) {
        // TODO - implement this
        return null;
    }
}
