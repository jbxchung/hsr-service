package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "accounts")
@Table(name = "accounts")
@Data
public class Account implements Serializable {
    @Serial
    private static final long serialVersionUID = 2610692721250148601L;

    @Id
    @Column(name = "account_id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "account_name")
    @JsonProperty("name")
    private String name;

    @Column(name = "email_address")
    @JsonProperty("email")
    private String email;

    @Column(name = "created")
    @JsonProperty("createdDate")
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(name = "password_enc")
    @JsonIgnore
    private String encryptedPassword;
}
