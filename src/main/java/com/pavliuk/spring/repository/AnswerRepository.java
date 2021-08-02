package com.pavliuk.spring.repository;

import com.pavliuk.spring.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
