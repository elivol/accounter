package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.service.AppCurrencyService;
import com.github.elivol.accounter.model.AppCurrency;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/admin/currency")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AppCurrencyController {

    private AppCurrencyService appCurrencyService;

    @GetMapping()
    public List<AppCurrency> findAll() {
        return appCurrencyService.findAll();
    }

    @PostMapping
    public AppCurrency create(@RequestBody AppCurrency currency) {
        return appCurrencyService.create(currency);
    }

}
