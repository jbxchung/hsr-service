package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {
}
