package com.github.elivol.accounter.dto.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Accessors(chain = true)
@ToString
public class OperationDto {

    @JsonIgnore
    private Long id;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @NotNull
    @PositiveOrZero
    private BigDecimal amount;

    @JsonProperty("is_incoming")
    private Boolean isIncoming = false;

    @JsonIgnore
    private Long accountId;

    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    @JsonProperty("account_id")
    public Long getAccountId() {
        return accountId;
    }
}
