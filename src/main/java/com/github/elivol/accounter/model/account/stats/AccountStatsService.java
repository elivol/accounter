package com.github.elivol.accounter.model.account.stats;

import com.github.elivol.accounter.model.account.AccountService;
import com.github.elivol.accounter.model.operation.Operation;
import com.github.elivol.accounter.model.operation.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountStatsService {

    private final OperationService operationService;
    private final AccountService accountService;

    @Transactional
    public AccountStats stats(Long account_id, LocalDateTime from, LocalDateTime to) {

        if (to == null) {
            to = LocalDateTime.now();
        }

        if (from == null || from.isBefore(LocalDateTime.of(LocalDate.EPOCH, LocalTime.MIDNIGHT))) {
            from = to.minusMonths(1L);
        }

        List<Operation> data = operationService.findByAccountAndPeriod(account_id, from, to);

        BigDecimal income = data.stream()
                .filter(Operation::getIsIncoming)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal outcome = data.stream()
                .filter(o -> !o.getIsIncoming())
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal balance = accountService.getCurrentBalance(account_id);

        return new AccountStats(from, to, income, outcome, balance);

    }
}
