package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.TestDto;
import com.pavliuk.spring.exception.SubjectNotFoundException;
import com.pavliuk.spring.exception.TestNotFoundException;
import com.pavliuk.spring.mapper.TestMapper;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private TestMapper testMapper;

    public TestEntity createTest(TestDto testDto) throws SubjectNotFoundException {
        return testRepository.save(testMapper.createTestFromDto(testDto));
    }

    public TestEntity findTest(Long testId) throws TestNotFoundException {
        return testRepository.findById(testId)
                .orElseThrow(TestNotFoundException::new);
    }
}
