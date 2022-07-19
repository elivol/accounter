package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.dto.mapper.AccountMapper;
import com.github.elivol.accounter.dto.mapper.OperationMapper;
import com.github.elivol.accounter.dto.model.AccountDto;
import com.github.elivol.accounter.dto.model.OperationDto;
import com.github.elivol.accounter.model.Account;
import com.github.elivol.accounter.hateoas.assembler.AccountModelAssembler;
import com.github.elivol.accounter.dto.model.AccountStats;
import com.github.elivol.accounter.hateoas.assembler.AccountStatsModelAssembler;
import com.github.elivol.accounter.service.AccountStatsService;
import com.github.elivol.accounter.model.Operation;
import com.github.elivol.accounter.hateoas.assembler.OperationModelAssembler;
import com.github.elivol.accounter.service.OperationService;
import com.github.elivol.accounter.model.user.User;
import com.github.elivol.accounter.service.user.AuthenticationService;
import com.github.elivol.accounter.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "me/accounts")
@AllArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final OperationService operationService;
    private final AccountModelAssembler accountModelAssembler;
    private final OperationModelAssembler operationModelAssembler;
    private final AccountStatsService accountStatsService;
    private final AccountStatsModelAssembler accountStatsModelAssembler;


    /*
    * Actions with accounts
    * */
    @GetMapping
    public CollectionModel<EntityModel<AccountDto>> findAll() {
        User user = AuthenticationService.getCurrentUser();

        List<EntityModel<AccountDto>> accounts = accountService.findByUser(user)
                .stream()
                .map(AccountMapper::toAccountDto)
                .map(accountModelAssembler::toModel)
                .toList();

        return CollectionModel.of(accounts, linkTo(methodOn(AccountController.class).findAll()).withSelfRel());
    }

    @GetMapping(path = "/{id}")
    public EntityModel<AccountDto> findById(@PathVariable Long id) {
        Account account = accountService.findById(id);
        return accountModelAssembler.toModel(AccountMapper.toAccountDto(account));
    }

    @PostMapping
    public void create(@Valid @RequestBody AccountDto account) {
        accountService.create(account);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        accountService.delete(id);
    }


    /*
    * Actions with operations of specific account
    * */

    @GetMapping(path = "/{account_id}/operations")
    public CollectionModel<EntityModel<OperationDto>> findAccountOperations(@PathVariable("account_id") Long accountId) {

        List<EntityModel<OperationDto>> operations = operationService.findAccountOperations(accountId)
                .stream()
                .map(OperationMapper::toOperationDto)
                .map(operationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(
                operations,
                linkTo(methodOn(AccountController.class).findAccountOperations(accountId)).withSelfRel()
        );
    }

    @GetMapping(path = "/{account_id}/operations/{id}")
    public EntityModel<OperationDto> findOperationByIdAndAccount(
            @PathVariable("account_id") Long accountId,
            @PathVariable Long id) {

        return operationModelAssembler.toModel(
                OperationMapper.toOperationDto(operationService.findByIdAndAccount(accountId, id)));
    }

    @PostMapping(path = "/{account_id}/operations")
    public void createOperation(@PathVariable("account_id") Long accountId, @Valid @RequestBody OperationDto operation) {
        operationService.create(accountId, operation);
    }


    /*
    * Actions with account statistics
    * */
    @GetMapping(path = "/{account_id}/stats")
    public EntityModel<AccountStats> stats(@PathVariable("account_id") Long accountId,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                           @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        AccountStats stats = accountStatsService.stats(accountId, from, to);
        return accountStatsModelAssembler.toModel(stats);
    }

}
