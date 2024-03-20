package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String emailAddress);
    Optional<User> findByAccountName(String accountName);
}
