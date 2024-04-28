package dev.jbxchung.hsr.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serial;
import java.io.Serializable;

public class Inventory implements Serializable {
    @Serial
    private static final long serialVersionUID = -8857407772723113131L;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_account_name", referencedColumnName = "account_name")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", referencedColumnName = "item")
    private Item item;

    @Column(name = "quantity")
    @JsonProperty("quantity")
    private Long quantity;
}
