package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "items")
@Table(name = "items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Item implements Serializable {
    @Serial
    private static final long serialVersionUID = -3108749606993347417L;

    @Id
    @Column(name = "id")
    @JsonProperty("id")
    private String id;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    // todo - flesh this out with more attributes
}
