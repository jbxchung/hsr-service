package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.Friendship;
import dev.jbxchung.hsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friendship, Friendship.FriendKey> {

    @Query("SELECT f FROM friendships f WHERE f.key.sender = :userId OR f.key.receiver = :userId")
    List<Friendship> getFriends(@Param("userId") Long userId);
}
