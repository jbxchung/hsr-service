package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.GachaPull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class GachaPullResponse {
    private String username;
    private List<GachaPull> pullResults;
}
