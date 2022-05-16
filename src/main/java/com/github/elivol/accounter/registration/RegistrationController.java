package com.github.elivol.accounter.registration;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request, HttpServletRequest httpServletRequest) {
        String URL = "http://" +
                httpServletRequest.getServerName() + ":" +
                httpServletRequest.getServerPort() +
                httpServletRequest.getRequestURI();
        return registrationService.register(request, URL);
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam String token) {
        return registrationService.confirm(token);
    }
}
