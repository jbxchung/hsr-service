package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.dto.GachaPullRequest;
import dev.jbxchung.hsr.entity.*;
import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.repository.GachaPullRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class GachaPullService {
    Logger logger = LoggerFactory.getLogger(GachaPullService.class);

    @Autowired
    private GachaPullRepository gachaPullRepository;

    @Autowired
    private LightConeService lightConeService;

    @Autowired
    private CharacterService characterService;

    public List<GachaPull> getPullHistory(User user) {
        return gachaPullRepository.findByUserOrderByTimestampDesc(user);
    }

    public GachaPull recordPull(User user, GachaPullRequest gachaPullRequest) {
        GachaEntity pullResult = null;
        if (gachaPullRequest.getEntityType() == Character.class) {
            pullResult = characterService.getById(gachaPullRequest.getEntityId());
        }
        if (gachaPullRequest.getEntityType() == LightCone.class) {
            pullResult = lightConeService.getById(gachaPullRequest.getEntityId());
        }

        GachaPull pull = GachaPull.builder()
                .user(user)
                .entityType(gachaPullRequest.getEntityType())
                .pullResult(pullResult)
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        return gachaPullRepository.save(pull);
    }

    public GachaPull deletePull(User user, Long pullId) {
        GachaPull pullToDelete = gachaPullRepository.findById(pullId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find gacha pull with id " + pullId));;

        if (!Objects.equals(pullToDelete.getUser().getId(), user.getId())) {
            throw new IllegalArgumentException("Cannot delete a pull of a different user");
        }

        gachaPullRepository.delete(pullToDelete);

        return pullToDelete;
    }
}
