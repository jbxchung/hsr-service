package dev.jbxchung.hsr.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dev.jbxchung.hsr.configuration.EntityClassNameDeserializer;
import dev.jbxchung.hsr.entity.GachaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PullRequest {
    private String entityId;
    @JsonDeserialize(using = EntityClassNameDeserializer.class)
    private Class<? extends GachaEntity> entityType;
}
