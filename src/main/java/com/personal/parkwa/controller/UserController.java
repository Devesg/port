package com.personal.parkwa.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.personal.parkwa.dto.UserForm;
import com.personal.parkwa.entity.User;
import com.personal.parkwa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(value = "/login")
    public String loginform() {
        return "/login/login";
    }

    @GetMapping(value = "/join")
    public String joinform() {

        return "/login/join";
    }

    // 로그인 요청
    @PostMapping(value = "/login")
    public String login(UserForm form, Model model) {

        User user = userService.login(form.getName(), form.getEmail(), form.getPassword());

        return "/login/login";
    }

    // 내정보
    @GetMapping(value = "/info")
    public String info(Principal principal, Model model) {
        User findUser = userService.findbyUserName(principal.getName());

        model.addAttribute("user", findUser);

        return "/user/info";
    }

    // 내정보
    @GetMapping(value = "/delete")
    public String deleteUser(long userId) {
        userService.doDelete(userId);

        // 인증 정보 삭제
        SecurityContextHolder.clearContext();

        return "redirect:/logout";
    }

    // 회원가입 요청
    @PostMapping(value = "/join")
    public String join(@Valid UserForm form, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            // 유효성 검사 에러가 발생한 경우
            List<ObjectError> errors = bindingResult.getAllErrors();

            StringBuilder errorMsg = new StringBuilder();
            for (ObjectError error : errors) {
                errorMsg.append(error.getDefaultMessage()).append("\t ");
            }
            log.info("error msg : {} ", errorMsg);
            model.addAttribute("errorMessage", errorMsg);
            return "/login/join";
        }
        userService.join(form.getName(), form.getEmail(), form.getPassword());

        return "/login/login";
    }

}
