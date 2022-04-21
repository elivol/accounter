package com.github.elivol.accounter.registration;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/register")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping
    public String register(@RequestBody RegistrationRequest request) {
        return registrationService.register(request);
    }

    @GetMapping(path = "/confirm")
    public void confirm(@RequestParam String token) {
        registrationService.confirm(token);
    }
}
