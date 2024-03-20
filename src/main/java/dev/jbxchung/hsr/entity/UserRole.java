package dev.jbxchung.hsr.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "user_roles")
@Table(name = "user_roles")
@Data
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private Role role;

    public enum Role {
        ROLE_ADMIN,
        ROLE_USER
    }
}
