package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.dto.GachaPullDTO;
import dev.jbxchung.hsr.dto.PullRequest;
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

    public GachaPull recordPull(User user, PullRequest pullRequest) {
        GachaEntity pullResult = null;
        if (pullRequest.getEntityType() == Character.class) {
            pullResult = characterService.getById(pullRequest.getEntityId());
        }
        if (pullRequest.getEntityType() == LightCone.class) {
            pullResult = lightConeService.getById(pullRequest.getEntityId());
        }

        GachaPull pull = GachaPull.builder()
                .user(user)
                .pullResult(pullResult)
                .timestamp(Timestamp.from(Instant.now()))
                .build();

        return gachaPullRepository.save(pull);
    }

    public GachaPullDTO getDTO(GachaPull... pulls) {
        return GachaPullDTO.builder()
                .username(pulls[0].getUser().getAccountName())
                .pullResults(Arrays.stream(pulls).toList())
                .build();
    }
}
