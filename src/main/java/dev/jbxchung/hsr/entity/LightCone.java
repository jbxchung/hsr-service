package dev.jbxchung.hsr.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@Entity(name = "light_cones")
@Table(name = "light_cones")
@Data
@EqualsAndHashCode(callSuper = true)
public class LightCone extends GachaEntity {
    @Serial
    private static final long serialVersionUID = 1849061056850872703L;

}
