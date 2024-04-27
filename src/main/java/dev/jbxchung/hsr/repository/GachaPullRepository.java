package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.GachaPull;
import dev.jbxchung.hsr.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GachaPullRepository extends JpaRepository<GachaPull, Long> {
    List<GachaPull> findByUserOrderByTimestampDesc(User user);
}
