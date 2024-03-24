package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "characters")
@Table(name = "characters")
@Data
public class Character implements Serializable {
    @Serial
    private static final long serialVersionUID = 1372317909086804393L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "name", unique = true)
    @JsonProperty("name")
    private String name;

    @Column(name = "rarity")
    @JsonProperty("rarity")
    private Integer rarity;

    @Enumerated(EnumType.STRING)
    @Column(name = "path")
    @JsonProperty("path")
    private Path path;

    @Enumerated(EnumType.STRING)
    @Column(name = "element")
    @JsonProperty("element")
    private Element element;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "thumbnailFilePath")
    @JsonIgnore
    private String thumbnailFilePath;

    @Getter
    public enum Path {
        ABUNDANCE("Abundance"),
        DESTRUCTION("Destruction"),
        ERUDITION("Erudition"),
        HARMONY("Harmony"),
        HUNT("Hunt"),
        NIHILITY("Nihility"),
        PRESERVATION("Preservation");

        private final String value;
        Path(String value) { this.value = value; }
    }

    @Getter
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
    }
}
