package com.pavliuk.spring.web;

import com.pavliuk.spring.model.Subject;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class MainController {
    private static final int DEFAULT_TEST_PER_PAGE = 6;
    private static final int DEFAULT_PAGE = 1;

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
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new User());

        return "signup";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        //TODO write a dto with Bean Validation 2.0(https://www.baeldung.com/javax-validation)
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoleId((long) 2);
        userRepository.save(user);

        return "register_success";
    }
}
