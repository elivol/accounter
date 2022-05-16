package com.github.elivol.accounter.entity.operation;

import com.github.elivol.accounter.entity.account.Account;
import com.github.elivol.accounter.entity.account.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    // TODO: использоваь limit
    public List<Operation> findAccountOperations(Long account_id) {
        Account account = accountService.findById(account_id);
        return operationRepository.findByAccount(account);
    }

    public List<Operation> findAll() {
        return operationRepository.findAll();
    }

    public Operation findByIdAndAccount(Long account_id, Long id) {
        Account account = accountService.findById(account_id);
        return operationRepository.findByAccountAndId(account, id).orElseThrow(
                () -> new NoSuchElementException(String.format(OPERATION_WITH_ID_NOT_FOUND, id))
        );
    }

    public Operation findById(Long id) {
        return operationRepository.findById(id).orElseThrow(
                () -> new IllegalStateException(String.format(OPERATION_WITH_ID_NOT_FOUND, id))
        );
    }

    public void create(Long account_id, Operation operation) {
        Account account = accountService.findById(account_id);
        operation.setAccount(account);
        operation.setCreatedAt(LocalDateTime.now());
        operationRepository.save(operation);

        BigDecimal newBalance = (
                operation.getIsIncoming() ?
                operation.getAmount() :
                operation.getAmount().negate()
        ).add(account.getBalance());

        accountService.updateBalance(account_id, newBalance);
    }

    public void delete(Long id) {
        if (!operationRepository.existsById(id)) {
            throw new NoSuchElementException(String.format(OPERATION_WITH_ID_NOT_FOUND, id));
        }
        operationRepository.deleteById(id);
    }

    public void update(Long id, Operation operation) {

        Operation existingOperation = this.findById(id);

        existingOperation.setAmount(operation.getAmount());
        existingOperation.setIsIncoming(operation.getIsIncoming());
        existingOperation.setAccount(operation.getAccount());

        operationRepository.save(existingOperation);
    }

}
