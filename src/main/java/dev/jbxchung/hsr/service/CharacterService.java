package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Character;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface CharacterService {
    List<Character> getAll();
    Character get(String characterName) throws EntityNotFoundException;
    Character save(Character character);
    Character delete(String characterName) throws EntityNotFoundException;
}
