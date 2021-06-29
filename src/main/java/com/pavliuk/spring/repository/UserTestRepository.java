package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.UserTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTestRepository extends JpaRepository<UserTest, Long> {
    List<UserTest> findAllByUserId(Long userId);
}
