package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.GachaEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class GachaEntityDTO {
    private String id;
    private String name;
    private Integer rarity;
    private GachaEntity.Path path;
    private String description;
    private MultipartFile thumbnail;
}
