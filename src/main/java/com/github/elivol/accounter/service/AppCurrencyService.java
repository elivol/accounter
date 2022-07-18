package com.github.elivol.accounter.service;

import com.github.elivol.accounter.model.AppCurrency;
import com.github.elivol.accounter.repository.AppCurrencyRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppCurrencyService {

    private static final String CURRENCY_EXISTS = "Currency with code %s already exists";
    private static final String CURRENCY_NOT_EXISTS = "Currency with code %s does not exist";

    private final AppCurrencyRepository appCurrencyRepository;
    private final List<AppCurrency> supportedCurrencies = new ArrayList<>();

    public AppCurrencyService(AppCurrencyRepository appCurrencyRepository) {
        this.appCurrencyRepository = appCurrencyRepository;
        updateSupportedCurrencies();
    }

    public List<AppCurrency> findAll() {
        return appCurrencyRepository.findAll();
    }

    public AppCurrency findByCurrencyCode(String currencyCode) {
        return appCurrencyRepository.findByCurrencyCode(currencyCode)
                .orElseThrow(
                        () -> new IllegalStateException(String.format(CURRENCY_NOT_EXISTS, currencyCode))
                );
    }

    @Transactional
    public AppCurrency create(AppCurrency currency) {
        boolean currencyPresent = appCurrencyRepository.findByCurrencyCode(currency.getCurrencyCode()).isPresent();

        if (currencyPresent) {
            throw new IllegalStateException(String.format(CURRENCY_EXISTS, currency.getCurrencyCode()));
        }

        AppCurrency saved = appCurrencyRepository.save(currency);
        updateSupportedCurrencies();
        return saved;
    }

    public List<AppCurrency> getSupportedCurrencies() {
        return List.copyOf(supportedCurrencies);
    }

    public void updateSupportedCurrencies() {
        supportedCurrencies.clear();
        supportedCurrencies.addAll(appCurrencyRepository.findAll());
    }

}
