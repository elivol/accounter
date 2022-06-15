package com.github.elivol.accounter.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

    private String result;

    private Instant timeLastUpdate;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates;


    @JsonProperty("time_last_update")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "EEE, dd MMM yyyy HH:mm:ss (Z)", timezone = "+0000")
    public Instant getTimeLastUpdate() {
        return timeLastUpdate;
    }

    @JsonProperty("time_last_update_unix")
    public void setTimeLastUpdate(Instant timeLastUpdate) {
        this.timeLastUpdate = timeLastUpdate;
    }
}
