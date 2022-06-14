package com.github.elivol.accounter.registration;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@Valid @RequestBody RegistrationRequest request, HttpServletRequest httpServletRequest) {
        String URL = httpServletRequest.getRequestURL().toString();
        return registrationService.register(request, URL);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam String token) {
        return registrationService.confirm(token);
    }
}
