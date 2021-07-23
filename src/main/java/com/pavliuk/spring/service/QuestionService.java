package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.QuestionDto;
import com.pavliuk.spring.exception.QuestionNotFoundException;
import com.pavliuk.spring.mapper.QuestionMapper;
import com.pavliuk.spring.model.Question;
import com.pavliuk.spring.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    public Question createQuestion(QuestionDto questionDto) {
        Question question = QuestionMapper.createQuestionFromDto(questionDto);
        return repository.save(question);
    }

    public Question findById(Long id) throws QuestionNotFoundException {
        return repository
                .findById(id)
                .orElseThrow(QuestionNotFoundException::new);
    }

    public void deleteQuestion(Long questionId) {
        repository.deleteById(questionId);
    }
}
