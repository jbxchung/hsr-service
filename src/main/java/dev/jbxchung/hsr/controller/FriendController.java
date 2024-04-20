package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.FriendshipDTO;
import dev.jbxchung.hsr.entity.Friendship;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.service.FriendService;
import dev.jbxchung.hsr.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private FriendService friendService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> getFriends(HttpServletRequest request) {
        String callerUsername = request.getRemoteUser();
        User caller = userDetailsService.getUser(callerUsername);
        List<Friendship> friends = friendService.getFriends(caller);

        List<FriendshipDTO> responseBody = friends.stream().map(friendService::getDTO).toList();

        return ResponseEntity.ok(new ApiResponse<>(true, responseBody));
    }

    @PostMapping("/request/{targetUser}")
    public ResponseEntity<?> requestFriend(@PathVariable String targetUser, HttpServletRequest request) {
        String caller = request.getRemoteUser();

        User requester = userDetailsService.getUser(caller);
        // todo - handle user not found
        User receiver = userDetailsService.getUser(targetUser);

        Friendship friendRequest = friendService.request(requester, receiver);
        FriendshipDTO friendshipDTO = friendService.getDTO(friendRequest);

        return ResponseEntity.ok(new ApiResponse<>(true, friendshipDTO));
    }

    @PostMapping("/accept/{sender}")
    public ResponseEntity<?> acceptFriend(@PathVariable String sender, HttpServletRequest request) {
        String caller = request.getRemoteUser();

        User receiver = userDetailsService.getUser(caller);
        User requester = userDetailsService.getUser(sender);

        Friendship friendAccept = friendService.accept(requester, receiver);
        FriendshipDTO friendshipDTO = friendService.getDTO(friendAccept);

        return ResponseEntity.ok(new ApiResponse<>(true, friendshipDTO));
    }

}
