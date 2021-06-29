package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.TestDto;
import com.pavliuk.spring.exception.SubjectNotFoundException;
import com.pavliuk.spring.exception.TestNotFoundException;
import com.pavliuk.spring.mapper.TestMapper;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.model.UserTest;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private TestMapper testMapper;

    public TestEntity createTest(TestDto testDto) throws SubjectNotFoundException {
        return testRepository.save(testMapper.createTestFromDto(testDto));
    }

    public TestEntity findTest(Long testId) throws TestNotFoundException {
        return testRepository.findById(testId)
                .orElseThrow(TestNotFoundException::new);
    }

    public UserTest assignTestToUser(Long testId, User user) throws TestNotFoundException {
        UserTest test = userTestRepository.findByTestAndUser(findTest(testId), user)
                .orElse(testMapper.createUserTest(findTest(testId), user));

        return userTestRepository.save(test);
    }
}
