package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String username);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET isBlocked = 1 WHERE id = :id")
    int blockUserById(@Param("id") Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET isBlocked = 0 WHERE id = :id")
    int unblockUserById(@Param("id") Long userId);
}
