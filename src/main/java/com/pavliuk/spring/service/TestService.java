package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.TestDto;
import com.pavliuk.spring.exception.SubjectNotFoundException;
import com.pavliuk.spring.exception.TestNotFoundException;
import com.pavliuk.spring.mapper.TestMapper;
import com.pavliuk.spring.model.*;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class TestService {
    public static final String USER_TEST_SESSION_KEY = "userTest";

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
        return userTestRepository.findByTestAndUserAndFinishedAtIsNull(findTest(testId), user)
                .orElse(createUserTest(testId, user));
    }

    public UserTest createUserTest(Long testId, User user) throws TestNotFoundException {
        UserTest test = testMapper.createUserTest(findTest(testId), user);
        return userTestRepository.save(test);
    }

    public void saveUserTestToSession(UserTest userTest, HttpSession session) {
        session.setAttribute(USER_TEST_SESSION_KEY, new UserTestWrapper(userTest));
    }

    public UserTestWrapper getCurrentTestFromSession(HttpSession session) {
        return (UserTestWrapper) session.getAttribute(USER_TEST_SESSION_KEY);
    }

    public void finishPreviousTest(HttpSession session) {
        userTestRepository.finishAllTests(new Date());
    }

    public double finishCurrentTest(HttpSession session) throws TestNotFoundException {
        UserTestWrapper testWrapper = getCurrentTestFromSession(session);
        UserTest test = userTestRepository.findById(testWrapper.getId())
                .orElseThrow(TestNotFoundException::new);

        test.setFinishedAt(new Date());
        test.setScore(getTestScore(testWrapper));
        userTestRepository.save(test);

        return test.getScore();
    }

    public Page<TestEntity> getTestPage(Optional<List<Long>> selectedSubjects, Pageable pageable) {
        return selectedSubjects.isPresent()
                ? testRepository.findAllBySubjectIdIn(selectedSubjects.get(), pageable)
                : testRepository.findAll(pageable);
    }

    private double getTestScore(UserTestWrapper test) {
        long correctQuestionsCount = test.getQuestions()
                .stream()
                .filter(QuestionWrapper::isAnsweredCorrectly)
                .count();

        return (double)correctQuestionsCount / test.getQuestions().size() * 100;
    }
}
