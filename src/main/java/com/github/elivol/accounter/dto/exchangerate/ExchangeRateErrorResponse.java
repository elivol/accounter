package com.github.elivol.accounter.dto.exchangerate;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ExchangeRateErrorResponse extends ExchangeRate {

    @JsonProperty("error-type")
    private String errorType;

    public ExchangeRateErrorResponse(String result, String errorType) {
        super(result);
        this.errorType = errorType;
    }
}
