package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.repository.CharacterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public List<Character> getAll() {
        return characterRepository.findAll();
    }

    @Override
    public Character get(String characterName) throws EntityNotFoundException {
        return characterRepository.findByName(characterName)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find character with name " + characterName));
    }

    @Override
    public Character save(Character character) {
        return characterRepository.save(character);
    }

    @Override
    public Character delete(String characterName) throws EntityNotFoundException {
        Character characterToDelete = characterRepository.findByName(characterName)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find character with name " + characterName));

        // todo - make sure this character object's lazy fields (if any) are fully loaded so the return value can be deserialized
        characterRepository.delete(characterToDelete);

        return characterToDelete;
    }
}
