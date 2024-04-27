package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.jbxchung.hsr.configuration.EntityClassNameDeserializer;
import dev.jbxchung.hsr.configuration.EntityClassNameSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity(name = "gacha_pulls")
@Table(name = "gacha_pulls")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GachaPull implements Serializable {
    @Serial
    private static final long serialVersionUID = -211643722310313709L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "timestamp")
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @Column(name = "gacha_entity_type")
    @JsonSerialize(using = EntityClassNameSerializer.class)
    @JsonProperty("entityType")
    private Class<? extends GachaEntity> entityType;

    @ManyToOne(optional = false)
    @JoinColumn(name = "gacha_entity_id", referencedColumnName = "id")
    @JsonProperty("entity")
    private GachaEntity pullResult;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_account_name", referencedColumnName = "account_name")
    @JsonIgnore
    private User user;
}
