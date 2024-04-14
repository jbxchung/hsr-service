package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serial;

@Entity(name = "characters")
@Table(name = "characters")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class Character extends GachaEntity {
    @Serial
    private static final long serialVersionUID = 1372317909086804393L;

    @Enumerated(EnumType.STRING)
    @Column(name = "element")
    @JsonProperty("element")
    private Element element;

    public enum Element {
        FIRE("Fire"),
        ICE("Ice"),
        IMAGINARY("Imaginary"),
        LIGHTNING("Lightning"),
        PHYSICAL("Physical"),
        QUANTUM("Quantum"),
        WIND("Wind");

        private final String value;
        Element(String value) { this.value = value; }
        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
