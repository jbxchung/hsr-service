package dev.jbxchung.hsr.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity(name = "light_cones")
@Table(name = "light_cones")
@Data
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class LightCone extends GachaEntity {
    @Serial
    private static final long serialVersionUID = 1849061056850872703L;
}
