package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "friendships")
@Table(name = "friendships")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Friendship implements Serializable {
    @Serial
    private static final long serialVersionUID = -6624091733971026303L;

    @EmbeddedId
    FriendKey key;

    @Column(name = "status")
    private FriendStatus status;

    public enum FriendStatus {
        ACCEPTED,
        CANCELLED,
        DELETED,
        REJECTED,
        REQUESTED
    }

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class FriendKey implements Serializable {
        @Serial
        private static final long serialVersionUID = -3048939285218971624L;

        @Column(name = "sender")
        @JsonProperty("sender")
        private Long sender;

        @Column(name = "receiver")
        @JsonProperty("receiver")
        private Long receiver;
    }
}
