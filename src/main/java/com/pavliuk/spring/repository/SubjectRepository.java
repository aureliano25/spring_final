package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
