package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
}
