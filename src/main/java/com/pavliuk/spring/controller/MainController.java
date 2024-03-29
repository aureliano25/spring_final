package com.pavliuk.spring.controller;

import com.pavliuk.spring.dto.SignUpFormDto;
import com.pavliuk.spring.exception.TestNotFoundException;
import com.pavliuk.spring.exception.UserAlreadyExistsException;
import com.pavliuk.spring.model.*;
import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.UserTestRepository;
import com.pavliuk.spring.service.TestService;
import com.pavliuk.spring.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.pavliuk.spring.util.UserUtil;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
public class MainController {
    private static final int DEFAULT_TEST_PER_PAGE = 6;

    @Autowired
    private UserService userService;

    @Autowired
    private TestService testService;

    @Autowired
    private UserTestRepository userTestRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping("/")
    public String getMainPage(
            @PageableDefault(value = DEFAULT_TEST_PER_PAGE) Pageable pageable,
            @RequestParam(name = "subjects") Optional<List<Long>> selectedSubjects,
            Model model
    ) {
        model.addAttribute("testsPage", testService.getTestPage(selectedSubjects, pageable));
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("selectedSubjects", selectedSubjects.orElseGet(ArrayList::new));

        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/signup")
    public String getRegistrationForm(@ModelAttribute("signUpForm") SignUpFormDto signUpForm) {
        return "signup";
    }

    @PostMapping("/signup")
    public String processRegister(
            @ModelAttribute("signUpForm") @Valid SignUpFormDto signUpForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        try {
            userService.registerNewUserAccount(signUpForm);
        } catch (UserAlreadyExistsException e) {
            bindingResult.rejectValue("login", "javax.validation.constraints.login.alreadyExists.message");
            return "signup";
        }

        return "register_success";
    }

    @RequestMapping("/statistics")
    public String getUserStatisticPage(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long currentUserId = 2L;
        if (principal instanceof CustomUserDetails) {
            currentUserId = ((CustomUserDetails) principal).getId();
        }
        model.addAttribute("tests", userTestRepository.findAllByUserId(currentUserId));

        return "user_statistic";
    }

    @RequestMapping("/test/start/{testId}")
    public String startTest(
            @PathVariable Long testId,
            HttpSession session
    ) throws TestNotFoundException {
        testService.finishPreviousTest(session);
        UserTest test = testService.assignTestToUser(testId, UserUtil.getCurrentUser());
        testService.saveUserTestToSession(test, session);

        return "redirect:/test/current";
    }

    @RequestMapping("/test/current")
    public String currentTest(HttpSession session, Model model) {
        UserTestWrapper currentTest = testService.getCurrentTestFromSession(session);
        model.addAttribute("test", currentTest);

        return "test";
    }

    @RequestMapping("/test/next")
    public String nextQuestion(
            @RequestParam(value = "options[]", required = false) List<Integer> options,
            @RequestParam("time_left") Integer timeLeft,
            HttpSession session
    ) {
        UserTestWrapper currentTest = testService.getCurrentTestFromSession(session);
        currentTest.update(options, timeLeft);

        if (currentTest.hasNext()) {
            currentTest.nextQuestion();
        }

        return "redirect:/test/current";
    }

    @RequestMapping("/test/previous")
    public String previousQuestion(
            @RequestParam(value = "options[]", required = false) List<Integer> options,
            @RequestParam("time_left") Integer timeLeft,
            HttpSession session
    ) {
        UserTestWrapper currentTest = testService.getCurrentTestFromSession(session);
        currentTest.update(options, timeLeft);

        if (currentTest.hasPrevious()) {
            currentTest.previousQuestion();
        }

        return "redirect:/test/current";
    }

    @RequestMapping("/test/finish")
    public String finishTest(
            @RequestParam(value = "options[]", required = false) List<Integer> options,
            HttpSession session,
            Model model
    ) throws TestNotFoundException {
        UserTestWrapper currentTest = testService.getCurrentTestFromSession(session);
        currentTest.update(options, 0);
        double testScore = testService.finishCurrentTest(session);

        log.info("finish test " + currentTest);
        model.addAttribute("test", currentTest);
        model.addAttribute("testResult", testScore);

        return "finish_test";
    }
}
