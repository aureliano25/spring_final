package com.pavliuk.spring.web;

import com.pavliuk.spring.dto.SignUpFormDto;
import com.pavliuk.spring.exception.UserAlreadyExistsException;
import com.pavliuk.spring.model.Subject;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserRepository;
import com.pavliuk.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private static final int DEFAULT_TEST_PER_PAGE = 6;
    private static final int DEFAULT_PAGE = 1;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping("/")
    public String getMainPage(
            @PageableDefault(value = DEFAULT_TEST_PER_PAGE, page = DEFAULT_PAGE) Pageable pageable,
            @RequestParam(name = "subjects") Optional<List<Long>> selectedSubjects,
            Model model
    ) {
        Page<TestEntity> testEntityPage = selectedSubjects.isPresent()
                ? testRepository.findAllBySubjectIdIn(selectedSubjects.get(), pageable)
                : testRepository.findAll(pageable);
        model.addAttribute("testsPage", testEntityPage);

        List<Subject> subjects = subjectRepository.findAll();
        model.addAttribute("subjects", subjects);

        model.addAttribute("selectedSubjects", selectedSubjects.orElseGet(ArrayList::new));

        //TODO replace everything to constants
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
    public String processRegister(@ModelAttribute("signUpForm") @Valid SignUpFormDto signUpForm, BindingResult bindingResult) {
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
}
