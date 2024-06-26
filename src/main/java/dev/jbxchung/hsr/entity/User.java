package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Entity(name = "users")
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "account_name"),
        @UniqueConstraint(columnNames = "email_address")
})
@Data
public class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 2610692721250148601L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "created")
    @JsonProperty("createdDate")
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(name = "account_name")
    @JsonProperty("username")
    private String accountName;

    @Column(name = "email_address", unique = true)
    @JsonProperty("email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 20)
    @JsonProperty("role")
    private Role role;

    @Column(name = "password_enc")
    @JsonIgnore
    private String encryptedPassword;

    public enum Role {
        ADMIN,
        USER
    }
}
