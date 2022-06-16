package com.github.elivol.accounter.model.exchangerate;

import com.github.elivol.accounter.exception.ExchangeRateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ExchangeRateService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${external-api.exchange-rate.secret-key}")
    private String exchangeRateSecret;
    @Value("${external-api.exchange-rate.uri}")
    private String uri;

    public ExchangeRate rate(String baseCurrency) {

        ExchangeFilterFunction errorResponseFilter = ExchangeFilterFunction
                .ofResponseProcessor(ExchangeRateService::exchangeFilterResponseProcessor);

        ExchangeRate rate = webClientBuilder.filter(errorResponseFilter)
                .build()
                .get()
                .uri(uri, exchangeRateSecret, baseCurrency)
                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(ExchangeRateException::new))
//                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(ExchangeRateException::new))
                .bodyToMono(ExchangeRateResponse.class)
                .block();
        return rate;
    }

    private static Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        HttpStatus status = response.statusCode();
        if (status.is4xxClientError() || status.is5xxServerError()) {

            return response
                    .bodyToMono(ExchangeRateErrorResponse.class)
                    .flatMap(o -> Mono.error(new ExchangeRateException(o.getResult(), o.getErrorType())));
        }

        return Mono.just(response);
    }

}
