package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.dto.FriendshipDTO;
import dev.jbxchung.hsr.entity.Friendship;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.repository.FriendRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public List<Friendship> getFriends(User user) {
        return friendRepository.getFriends(user.getId());
    }

    public Friendship request(User sender, User receiver) {
        // todo - handle existing request / already friends
        Friendship friendRequest = new Friendship(
                new Friendship.FriendKey(sender.getId(), receiver.getId()),
                Friendship.FriendStatus.REQUESTED
        );
        return friendRepository.save(friendRequest);
    }

    public Friendship cancel(User sender, User receiver) {
        Friendship friendRequest = friendRepository.getFriendship(sender.getId(), receiver.getId())
                .orElseThrow(() -> new EntityNotFoundException("Unable to find friend request from " + sender.getAccountName() + " to " + receiver.getAccountName()));

        friendRepository.delete(friendRequest);
        friendRequest.setStatus(Friendship.FriendStatus.CANCELLED);

        return friendRequest;
    }

    public Friendship accept(User sender, User receiver) {
        Friendship friendRequest = friendRepository.getFriendship(sender.getId(), receiver.getId())
                .orElseThrow(() -> new EntityNotFoundException("Unable to find friend request from " + sender.getAccountName() + " to " + receiver.getAccountName()));

        friendRequest.setStatus(Friendship.FriendStatus.ACCEPTED);

        return friendRepository.save(friendRequest);
    }

    public Friendship reject(User sender, User receiver) {
        Friendship friendRequest = friendRepository.getFriendship(sender.getId(), receiver.getId())
                .orElseThrow(() -> new EntityNotFoundException("Unable to find friend request from " + sender.getAccountName() + " to " + receiver.getAccountName()));

        friendRepository.delete(friendRequest);
        friendRequest.setStatus(Friendship.FriendStatus.REJECTED);

        return friendRequest;
    }

    public Friendship delete(User sender, User receiver) {
        Friendship friendRequest = friendRepository.getFriendship(sender.getId(), receiver.getId())
                .or(() -> friendRepository.getFriendship(receiver.getId(), sender.getId()))
                .orElseThrow(() -> new EntityNotFoundException("Unable to find friend request between " + sender.getAccountName() + " and " + receiver.getAccountName()));

        friendRepository.delete(friendRequest);
        friendRequest.setStatus(Friendship.FriendStatus.DELETED);

        return friendRequest;
    }

    public FriendshipDTO getDTO(Friendship friendship) {
        String sender;
        try {
            User senderUser = userDetailsService.getUser(friendship.getKey().getSender());
            sender = senderUser.getAccountName();
        } catch (UsernameNotFoundException e) {
            sender = "[deleted user]";
        }
        String receiver;
        try {
            User receiverUser = userDetailsService.getUser(friendship.getKey().getReceiver());
            receiver = receiverUser.getAccountName();
        } catch (UsernameNotFoundException e) {
            receiver = "[deleted user]";
        }
        return new FriendshipDTO(
                sender,
                receiver,
                friendship.getStatus()
        );
    }
}
