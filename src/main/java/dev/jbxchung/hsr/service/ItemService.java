package dev.jbxchung.hsr.service;

import dev.jbxchung.hsr.entity.Item;
import dev.jbxchung.hsr.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item getItem(String itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find item with id " + itemId));
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item deleteItem(String itemId) {
        Item item = this.getItem(itemId);

        itemRepository.delete(item);

        return item;
    }
}
