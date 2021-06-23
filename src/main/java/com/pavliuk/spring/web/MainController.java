package com.pavliuk.spring.web;

import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.model.User;
import com.pavliuk.spring.repository.TestRepository;
import com.pavliuk.spring.repository.UserRepository;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @RequestMapping("/")
    public String getMainPage(Model model) {
        List<TestEntity> tests = testRepository.findAll();
        model.addAttribute("tests", tests);

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
        user.setRoleId((long)2);
        userRepository.save(user);

        return "register_success";
    }
}
