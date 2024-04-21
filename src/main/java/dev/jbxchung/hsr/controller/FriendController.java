package dev.jbxchung.hsr.controller;

import dev.jbxchung.hsr.dto.ApiResponse;
import dev.jbxchung.hsr.dto.FriendRequest;
import dev.jbxchung.hsr.dto.FriendshipDTO;
import dev.jbxchung.hsr.entity.Friendship;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.service.FriendService;
import dev.jbxchung.hsr.service.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @PostMapping("/request")
    public ResponseEntity<?> requestFriend(@RequestBody FriendRequest friendRequest, HttpServletRequest request) {
        String caller = request.getRemoteUser();

        try {
            User requester = userDetailsService.getUser(caller);
            User receiver = userDetailsService.getUser(friendRequest.getUser());

            Friendship sentFriendRequest = friendService.request(requester, receiver);
            FriendshipDTO friendshipDTO = friendService.getDTO(sentFriendRequest);

            return ResponseEntity.ok(new ApiResponse<>(true, friendshipDTO));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelFriend(@RequestBody FriendRequest friendRequest, HttpServletRequest request) {
        String caller = request.getRemoteUser();

        try {
            User requester = userDetailsService.getUser(caller);
            User receiver = userDetailsService.getUser(friendRequest.getUser());

            Friendship cancelFriendRequest = friendService.cancel(requester, receiver);
            FriendshipDTO friendshipDTO = friendService.getDTO(cancelFriendRequest);

            return ResponseEntity.ok(new ApiResponse<>(true, friendshipDTO));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptFriend(@RequestBody FriendRequest friendRequest, HttpServletRequest request) {
        String caller = request.getRemoteUser();

        try {
            User receiver = userDetailsService.getUser(caller);
            User requester = userDetailsService.getUser(friendRequest.getUser());

            Friendship friendAccept = friendService.accept(requester, receiver);
            FriendshipDTO friendshipDTO = friendService.getDTO(friendAccept);

            return ResponseEntity.ok(new ApiResponse<>(true, friendshipDTO));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectFriend(@RequestBody FriendRequest friendRequest, HttpServletRequest request) {
        String caller = request.getRemoteUser();

        try {
            User receiver = userDetailsService.getUser(caller);
            User requester = userDetailsService.getUser(friendRequest.getUser());

            Friendship friendAccept = friendService.reject(requester, receiver);
            FriendshipDTO friendshipDTO = friendService.getDTO(friendAccept);

            return ResponseEntity.ok(new ApiResponse<>(true, friendshipDTO));
        } catch (UsernameNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse<>(false, "User not found"), HttpStatus.BAD_REQUEST);
        }
    }


}
