package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.dto.UserItemDTO;
import dev.jbxchung.hsr.entity.Item;
import dev.jbxchung.hsr.entity.User;
import dev.jbxchung.hsr.entity.UserItem;
import dev.jbxchung.hsr.repository.ItemRepository;
import dev.jbxchung.hsr.repository.UserItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserItemService {

    @Autowired
    private UserItemRepository userItemRepository;

    public List<UserItem> getInventory(User user) {
        return userItemRepository.findByUser(user);
    }

    public UserItem addUserItem(Item item, User user) {
        return addUserItem(item, user, 1L);
    }
    public UserItem addUserItem(Item item, User user, Long quantity) {
        UserItem.UserItemKey userItemId = new UserItem.UserItemKey(user, item);
        UserItem userItem = userItemRepository.findById(userItemId)
                .orElse(new UserItem(
                        new UserItem.UserItemKey(user, item),
                        0L
                ));

        Long existingQuantity = userItem.getQuantity();
        userItem.setQuantity(existingQuantity + quantity);

        return userItemRepository.save(userItem);
    }

    public UserItem removeUserItem(Item item, User user, Long quantity) {
        UserItem.UserItemKey userItemId = new UserItem.UserItemKey(user, item);
        Optional<UserItem> existingUserItem = userItemRepository.findById(userItemId);

        if (existingUserItem.isEmpty()) {
            throw new EntityNotFoundException("User does not have item to remove: " + item.getId());
        }

        UserItem itemToDelete = existingUserItem.get();

        Long existingQuantity = itemToDelete.getQuantity();

        itemToDelete.setQuantity(existingQuantity - quantity);
        if (itemToDelete.getQuantity() < 0) {
            throw new IllegalArgumentException("Cannot remove more items than the user has: " + item.getId());
        } else if (itemToDelete.getQuantity() == 0) {
            // removed all items, delete this inventory entry
            userItemRepository.delete(itemToDelete);
        } else {
            // overwrite this inventory entry with new quantity
            userItemRepository.save(itemToDelete);
        }

        return itemToDelete;
    }
}
