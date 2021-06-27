package com.pavliuk.spring.web;

import com.pavliuk.spring.dto.response.Response;
import com.pavliuk.spring.repository.UserRepository;
import com.pavliuk.spring.service.UserService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping("/users")
    public String getUsersView(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "admin/users.html";
    }

    @RequestMapping("/block-user/{userId}")
    @ResponseBody
    public Response blockUser(@PathVariable Long userId) {
        return userService.blockUser(userId)
                ? Response.ok().setMessage("User was successfully blocked")
                : Response.badRequest().setMessage("Something went wrong");
    }

    @RequestMapping("/unblock-user/{userId}")
    @ResponseBody
    public Response unblockUser(@PathVariable Long userId) {
        return userService.unblockUser(userId)
                ? Response.ok().setMessage("User was successfully unblocked")
                : Response.badRequest().setMessage("Something went wrong");
    }

}
