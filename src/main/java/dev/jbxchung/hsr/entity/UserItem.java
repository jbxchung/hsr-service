package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.jbxchung.hsr.configuration.UserItemSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity(name = "user_items")
@Table(name = "user_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonSerialize(using = UserItemSerializer.class)
public class UserItem implements Serializable {
    @Serial
    private static final long serialVersionUID = -8857407772723113131L;

    @EmbeddedId
    private UserItemKey key;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Long quantity;

    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class UserItemKey implements Serializable {
        @Serial
        private static final long serialVersionUID = -8072092768267671665L;

        @ManyToOne(optional = false)
        @JoinColumn(name = "user_account_name", referencedColumnName = "account_name")
        @JsonIgnore
        private User user;

        @ManyToOne(optional = false)
        @JoinColumn(name = "item_id", referencedColumnName = "id")
        private Item item;
    }
}
