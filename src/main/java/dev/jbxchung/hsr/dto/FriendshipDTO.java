package dev.jbxchung.hsr.dto;

import dev.jbxchung.hsr.entity.Friendship;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class FriendshipDTO {
    String sender;
    String receiver;
    Friendship.FriendStatus status;
}
