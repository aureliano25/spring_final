package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.QuestionDto;
import com.pavliuk.spring.exception.QuestionNotFoundException;
import com.pavliuk.spring.mapper.QuestionMapper;
import com.pavliuk.spring.model.Question;
import com.pavliuk.spring.repository.AnswerRepository;
import com.pavliuk.spring.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    @Autowired
    private AnswerRepository answerRepository;

    @Transactional
    public Question updateQuestion(QuestionDto questionDto) {
        Question question = QuestionMapper.createQuestionFromDto(questionDto);
        repository.save(question);

        if (questionDto.getAnswersToDelete().size() != 0) {
            answerRepository.deleteAllById(questionDto.getAnswersToDelete());
        }

        question.getAnswers().forEach(a -> a.setQuestion(question));
        answerRepository.saveAll(question.getAnswers());
        return question;
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
