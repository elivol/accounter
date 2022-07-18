package com.github.elivol.accounter.service;

import com.github.elivol.accounter.dto.AccountStats;
import com.github.elivol.accounter.model.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccountStatsService {

    private final OperationService operationService;
    private final AccountService accountService;

    @Transactional
    public AccountStats stats(Long accountId, LocalDateTime from, LocalDateTime to) {

        if (to == null) {
            to = LocalDateTime.now();
        }

        if (from == null || from.isBefore(LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIDNIGHT))) {
            from = to.minusMonths(1L);
        }

        List<Operation> data = operationService.findByAccountAndPeriod(accountId, from, to);

        BigDecimal[] income = data.stream()
                .filter(Operation::getIsIncoming)
                .map(o -> new BigDecimal[]{o.getAmount(), BigDecimal.ONE})
                .reduce((result, next) -> new BigDecimal[]{result[0].add(next[0]), result[1].add(BigDecimal.ONE)})
                .orElse(new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE});

        BigDecimal[] outcome = data.stream()
                .filter(o -> !o.getIsIncoming())
                .map(o -> new BigDecimal[]{o.getAmount(), BigDecimal.ONE})
                .reduce((result, next) -> new BigDecimal[]{result[0].add(next[0]), result[1].add(BigDecimal.ONE)})
                .orElse(new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE});

        Map<String, BigDecimal> average = Map.of(
                "incoming", income[0].divide(income[1], RoundingMode.HALF_UP),
                "outcoming", outcome[0].divide(outcome[1], RoundingMode.HALF_UP));

        BigDecimal balance = accountService.getCurrentBalance(accountId);

        return new AccountStats(from, to, income[0], outcome[0], average, balance);

    }
}
