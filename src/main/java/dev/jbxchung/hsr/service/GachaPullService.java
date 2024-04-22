package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.dto.GachaPullResponse;
import dev.jbxchung.hsr.dto.GachaPullRequest;
import dev.jbxchung.hsr.entity.*;
import dev.jbxchung.hsr.entity.Character;
import dev.jbxchung.hsr.repository.GachaPullRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

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
        return gachaPullRepository.findByUser(user);
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
                .pullResult(pullResult)
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        return gachaPullRepository.save(pull);
    }

    public GachaPullResponse getDTO(GachaPull... pulls) {
        return GachaPullResponse.builder()
                .username(pulls[0].getUser().getAccountName())
                .pullResults(Arrays.stream(pulls).toList())
                .build();
    }
}
