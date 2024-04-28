package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.entity.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserItemRepository extends JpaRepository<UserItem, UserItem.UserItemKey> {
    @Query("SELECT userItem FROM user_items userItem WHERE userItem.key.user = :user")
    List<UserItem> findByUser(@Param("user") User user);
}
