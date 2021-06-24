package com.pavliuk.spring.web;

import com.pavliuk.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/users")
    public String getUsersView(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "admin/users.html";
    }
}
