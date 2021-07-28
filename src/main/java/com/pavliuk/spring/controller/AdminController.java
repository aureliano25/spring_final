package com.pavliuk.spring.controller;

import com.pavliuk.spring.dto.QuestionDto;
import com.pavliuk.spring.dto.SubjectDto;
import com.pavliuk.spring.dto.TestDto;
import com.pavliuk.spring.dto.UserDto;
import com.pavliuk.spring.dto.response.Response;
import com.pavliuk.spring.exception.QuestionNotFoundException;
import com.pavliuk.spring.exception.SubjectNotFoundException;
import com.pavliuk.spring.exception.TestNotFoundException;
import com.pavliuk.spring.model.Question;
import com.pavliuk.spring.model.TestEntity;
import com.pavliuk.spring.repository.SubjectRepository;
import com.pavliuk.spring.repository.UserRepository;
import com.pavliuk.spring.service.QuestionService;
import com.pavliuk.spring.service.SubjectService;
import com.pavliuk.spring.service.TestService;
import com.pavliuk.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private TestService testService;

    @Autowired
    private QuestionService questionService;

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

    @RequestMapping("/subject/delete/{subjectId}")
    @ResponseBody
    public Response deleteSubject(@PathVariable Long subjectId) {
        try {
            subjectService.deleteSubject(subjectId);
        } catch (Exception e) {
            return Response.badRequest().setMessage("Something went wrong");
        }

        return Response.ok().setMessage("Subject was successfully deleted");
    }


    @RequestMapping("/subject/update")
    @ResponseBody
    public Response updateSubject(@Valid SubjectDto subjectDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return Response.badRequest().setErrors(bindingResult.getAllErrors());
        }

        try {
            subjectService.updateSubject(subjectDto);
        } catch (Exception e) {
            return Response.badRequest().setMessage("Something went wrong");
        }

        return Response.ok().setMessage("Subject was successfully updated");
    }

    @GetMapping("/test/create")
    public String testCreationForm(
            @ModelAttribute("test")TestDto testDto,
            Model model
    ) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("difficulties", TestEntity.DIFFICULTY.values());

        return "/admin/create_test.html";
    }

    @PostMapping("/test/create")
    public String testCreationProcessing(
            @ModelAttribute("test") @Valid TestDto testDto,
            BindingResult bindingResult,
            Model model
    ) {
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("difficulties", TestEntity.DIFFICULTY.values());

        if (bindingResult.hasErrors()) {
            return "/admin/create_test.html";
        }

        try {
            TestEntity testEntity = testService.createTest(testDto);
            return "redirect:/admin/test/edit/" + testEntity.getId();
        } catch (SubjectNotFoundException e) {
            bindingResult.rejectValue("subject", "javax.validation.constraints.login.alreadyExists.message");
            return "/admin/create_test.html";
        }
    }

    @GetMapping("/test/edit/{testId}")
    public String editTestForm(
            @PathVariable Long testId,
            Model model
    ) throws TestNotFoundException {
        model.addAttribute("test", testService.findTest(testId));
        model.addAttribute("subjects", subjectRepository.findAll());
        model.addAttribute("difficulties", TestEntity.DIFFICULTY.values());

        return "/admin/update_test.html";
    }

    @PostMapping("/test/edit")
    @ResponseBody
    public Response editTestProcessing(
            @ModelAttribute("test") @Valid TestDto testDto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return Response.badRequest().setMessage("Unable to update data");
        }

        try {
            testService.createTest(testDto);
        } catch (SubjectNotFoundException e) {
            return Response.badRequest().setMessage("Unable to update data");
        }

        return Response.ok().setMessage("Updated");
    }

    @GetMapping("/question/create")
    public String createQuestionForm(@ModelAttribute("question") QuestionDto questionDto) {
        return "/admin/create_question.html";
    }

    @PostMapping(value = "/question/create", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response createQuestion(@RequestBody QuestionDto questionDto) {
        questionService.createQuestion(questionDto);
        return Response.ok();
    }

    @GetMapping(value = "/question/edit")
    public String updateQuestionForm(@RequestParam Long id, Model model) throws QuestionNotFoundException {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);

        return "/admin/update_question.html";
    }

    @PostMapping(value = "/question/edit", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public Response updateQuestion(@RequestBody QuestionDto questionDto) {
        questionService.createQuestion(questionDto);
        return Response.ok();
    }

    @PostMapping("/question/delete")
    @ResponseBody
    public Response deleteQuestion(@RequestParam("id") Long questionId) {
        questionService.deleteQuestion(questionId);

        return Response.ok().setMessage("Updated");
    }
}
