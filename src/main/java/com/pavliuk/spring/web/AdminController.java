package com.pavliuk.spring.web;

import com.pavliuk.spring.dto.UserDto;
import com.pavliuk.spring.dto.response.Response;
import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.UserRepository;
import com.pavliuk.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectRepository subjectRepository;

    @RequestMapping("/users")
    public String getUsersView(Model model) {
        model.addAttribute("users", userRepository.findAll());

        return "admin/users.html";
    }

    @RequestMapping("/subjects")
    public String getSubjectsView(Model model) {
        model.addAttribute("subjects", subjectRepository.findAll());
        return "/admin/subjects.html";
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

    @RequestMapping("/delete-user/{userId}")
    @ResponseBody
    public Response deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
        } catch (Exception e) {
            return Response.badRequest().setMessage("Something went wrong");
        }

        return Response.ok().setMessage("User was successfully deleted");
    }


    @ResponseBody
    @PostMapping("/update-user")
    public Response updateUser(@Valid UserDto userDto, BindingResult bindingResult) {
        return Response.ok().setMessage("ok");
    }
}
