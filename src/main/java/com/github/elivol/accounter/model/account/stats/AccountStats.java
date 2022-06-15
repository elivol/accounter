package com.github.elivol.accounter.model.account.stats;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountStats {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime from;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") private LocalDateTime to;
    private BigDecimal income;
    private BigDecimal outcome;
    @JsonProperty("current_balance") private BigDecimal currentBalance;

}
