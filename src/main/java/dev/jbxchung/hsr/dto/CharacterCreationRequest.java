package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.Character;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class CharacterCreationRequest extends GachaEntityCreationRequest {
    private Character.Element element;
}
