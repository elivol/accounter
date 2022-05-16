package com.github.elivol.accounter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

    @GetMapping
    public String getIndex() {
        return "index";
    }

    @GetMapping("login")
    public String getLogin() {
        return "login";
    }

    @GetMapping("logout")
    public String getProfile() {
        return "logout";
    }
}
