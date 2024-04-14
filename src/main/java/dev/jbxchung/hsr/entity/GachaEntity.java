package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@MappedSuperclass
public abstract class GachaEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 6836129875474288125L;

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private String id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "rarity")
    @JsonProperty("rarity")
    private Integer rarity;

    @Enumerated(EnumType.STRING)
    @Column(name = "path")
    @JsonProperty("path")
    private Path path;

    @Column(name = "description")
    @JsonProperty("description")
    private String description;

    @Column(name = "thumbnailFilePath")
    @JsonIgnore
    private String thumbnailFilePath;

    public enum Path {
        ABUNDANCE("Abundance"),
        DESTRUCTION("Destruction"),
        ERUDITION("Erudition"),
        HARMONY("Harmony"),
        NIHILITY("Nihility"),
        PRESERVATION("Preservation"),
        THE_HUNT("The Hunt");

        private final String value;
        Path(String value) { this.value = value; }
        @JsonValue
        public String getValue() {
            return value;
        }
    }
}
