package com.github.elivol.accounter.model.exchangerate;

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

        return webClientBuilder.filter(errorResponseFilter)
                .build()
                .get()
                .uri(uri, exchangeRateSecret, baseCurrency)
                .retrieve()
                //.onStatus(HttpStatus::is4xxClientError, response -> Mono.empty())
                .bodyToMono(ExchangeRate.class)
                .block();
    }

    private static Mono<ClientResponse> exchangeFilterResponseProcessor(ClientResponse response) {
        HttpStatus status = response.statusCode();
        if (status.is4xxClientError()) {
            return response.bodyToMono(ExchangeRate.class).flatMap(o -> Mono.empty());
        }
        /*if (status.is5xxServerError()) {
            return response.bodyToMono(String.class)
                    .flatMap(body -> Mono.error(new IllegalStateException(body)));
        }*/
        return Mono.just(response);
    }

}
