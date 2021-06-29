package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    List<UserTest> findAllByUserId(Long userId);
    Optional<UserTest> findByTestAndUser(TestEntity testEntity, User user);
}
