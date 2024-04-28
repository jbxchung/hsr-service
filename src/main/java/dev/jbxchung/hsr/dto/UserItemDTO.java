package dev.jbxchung.hsr.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserItemDTO {
    private String itemId;
    private Long quantity;
}
