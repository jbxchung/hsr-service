package dev.jbxchung.hsr.repository;

import dev.jbxchung.hsr.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
