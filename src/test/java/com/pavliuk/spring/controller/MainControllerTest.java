package com.pavliuk.spring.controller;

import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserRepository;
import com.pavliuk.spring.repository.UserTestRepository;
import com.pavliuk.spring.service.TestService;
import com.pavliuk.spring.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private TestService testService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private TestRepository testRepository;

    @MockBean
    private UserTestRepository userTestRepository;

    @MockBean
    private SubjectRepository subjectRepository;

    @Test
    public void testShowRegisterForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));
    }
}