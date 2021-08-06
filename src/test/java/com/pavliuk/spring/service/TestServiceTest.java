package com.pavliuk.spring.service;

import com.pavliuk.spring.dto.TestDto;
import com.pavliuk.spring.exception.SubjectNotFoundException;
import com.pavliuk.spring.exception.TestNotFoundException;
import com.pavliuk.spring.mapper.TestMapper;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserTestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestServiceTest {
    @InjectMocks
    private TestService testService;

    @Mock
    private TestRepository testRepository;

    @Mock
    private TestMapper testMapper;

    @Mock
    private UserTestRepository userTestRepository;

    @Test
    public void shouldCreateTest() throws SubjectNotFoundException {
        TestDto testDto = mock(TestDto.class);
        testService.createTest(testDto);
        verify(testRepository).save(any());
    }

    @Test
    public void findTestById() throws TestNotFoundException {
        when(testRepository.findById(1L)).thenReturn(Optional.of(new TestEntity()));
        testService.findTest(1L);
        verify(testRepository).findById(1L);
    }

    @Test
    public void finishPreviousTest() {
        HttpSession session = mock(HttpSession.class);
        testService.finishPreviousTest(session);
        verify(userTestRepository).finishAllTests(any());
    }

    @Test
    public void getCurrentTestFromSession() {
        HttpSession session = mock(HttpSession.class);
        testService.getCurrentTestFromSession(session);
        verify(session).getAttribute(TestService.USER_TEST_SESSION_KEY);
    }
}