package com.github.elivol.accounter.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRate {

    private String result;

    private Date timeLastUpdate;

    @JsonProperty("base_code")
    private String baseCode;

    @JsonProperty("conversion_rates")
    private Map<String, Double> conversionRates;

    @JsonProperty("time_last_update")
    @JsonFormat(pattern = "EEE, dd MMM yyyy HH:mm:ss Z")
    public Date getTimeLastUpdate() {
        return timeLastUpdate;
    }

    @JsonProperty("time_last_update_utc")
    public void setTimeLastUpdate(Date timeLastUpdate) {
        this.timeLastUpdate = timeLastUpdate;
    }

}
