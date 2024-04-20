package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Friendship;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.repository.FriendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {
    @Autowired
    private FriendRepository friendRepository;

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
}
