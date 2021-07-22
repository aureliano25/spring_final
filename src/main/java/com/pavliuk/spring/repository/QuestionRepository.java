package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
