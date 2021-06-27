package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.TestEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<TestEntity, Long> {
    Page<TestEntity> findAllBySubjectIdIn(List<Long> subjectId, Pageable pageable);
}
