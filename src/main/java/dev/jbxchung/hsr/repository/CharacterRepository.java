package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, String> {
    Optional<Character> findByName(String characterName);
}
