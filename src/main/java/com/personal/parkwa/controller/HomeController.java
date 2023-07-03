package com.personal.parkwa.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class HomeController {

    @GetMapping(value = "/index")
    public String home(@AuthenticationPrincipal User userInfo) {

        log.info("userinfo : {}", userInfo.getUsername());
        log.info("userinfo : {}", userInfo.getAuthorities());
        return "/index";
    }

    @GetMapping(value = "/")
    public String gotohome(@AuthenticationPrincipal User userInfo) {

        log.info("userinfo : {}", userInfo.getUsername());
        log.info("userinfo : {}", userInfo.getAuthorities());
        return "/index";
    }


}
