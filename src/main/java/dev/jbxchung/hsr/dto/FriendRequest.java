package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.Friendship;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FriendRequest {
    String user;
}
