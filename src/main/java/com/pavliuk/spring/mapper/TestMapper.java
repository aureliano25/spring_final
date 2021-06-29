package com.pavliuk.spring.mapper;

import com.pavliuk.spring.dto.TestDto;
import com.pavliuk.spring.exception.SubjectNotFoundException;
import com.pavliuk.spring.model.Subject;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.UserTest;
import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestMapper {
    @Autowired
    public SubjectRepository subjectRepository;

    @Autowired
    public UserTestRepository userTestRepository;

    public TestEntity createTestFromDto(TestDto testDto) throws SubjectNotFoundException {
        Subject subject = subjectRepository.findById(testDto.getSubject())
                .orElseThrow(SubjectNotFoundException::new);

        return TestEntity.builder()
                .id(testDto.getId())
                .subject(subject)
                .difficulty(testDto.getDifficulty())
                .timer(testDto.getTime())
                .title(testDto.getTitle())
                .build();
    }

    public UserTest createUserTest(TestEntity testEntity, User user) {
        return UserTest.builder()
                .test(testEntity)
                .user(user)
                .score(0.0)
                .build();
    }
}
