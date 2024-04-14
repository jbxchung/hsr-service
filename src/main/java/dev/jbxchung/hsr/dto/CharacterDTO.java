package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.Character;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CharacterDTO extends GachaEntityDTO {
    private Character.Element element;
}
