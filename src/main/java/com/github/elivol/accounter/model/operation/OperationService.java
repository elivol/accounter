package com.github.elivol.accounter.model.operation;

import com.github.elivol.accounter.exception.EntityNotFoundException;
import com.github.elivol.accounter.model.account.Account;
import com.github.elivol.accounter.model.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class OperationService {

    private static final String OPERATION_WITH_ID_NOT_FOUND = "Operation with id %d not found";
    private final OperationRepository operationRepository;
    private final AccountService accountService;

    @Transactional
    public List<Operation> findAccountOperations(Long account_id) {
        Account account = accountService.findById(account_id);
        return operationRepository.findByAccount(account);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    @Transactional
    public Operation findByIdAndAccount(Long accountId, Long id) {
        Account account = accountService.findById(accountId);
        return operationRepository.findByAccountAndId(account, id).orElseThrow(
                () -> new EntityNotFoundException(String.format(OPERATION_WITH_ID_NOT_FOUND, id))
        );
    }

    public Operation findById(Long id) {
        return operationRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format(OPERATION_WITH_ID_NOT_FOUND, id))
        );
    }

    @Transactional
    public void create(Long accountId, Operation operation) {
        Account account = accountService.findById(accountId);
        operation.setAccount(account);
        operation.setCreatedAt(LocalDateTime.now());
        operationRepository.save(operation);

        BigDecimal newBalance = (
                operation.getIsIncoming() ?
                operation.getAmount() :
                operation.getAmount().negate()
        ).add(account.getBalance());

        accountService.updateBalance(accountId, newBalance);
    }

    @Transactional
    public void delete(Long id) {
        if (!operationRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format(OPERATION_WITH_ID_NOT_FOUND, id));
        }
        operationRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, Operation operation) {

        Operation existingOperation = this.findById(id);

        existingOperation.setAmount(operation.getAmount());
        existingOperation.setIsIncoming(operation.getIsIncoming());
        existingOperation.setAccount(operation.getAccount());

        operationRepository.save(existingOperation);
    }

    public List<Operation> findByAccountAndPeriod(Long accountId, LocalDateTime from, LocalDateTime to) {
        Account account = accountService.findById(accountId);
        return operationRepository.findByAccountAndPeriod(account, from, to);
    }

}
