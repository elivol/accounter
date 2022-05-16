package com.github.elivol.accounter.entity.operation;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/me/operations")
@AllArgsConstructor
public class OperationController {

    private final OperationService operationService;

    @GetMapping
    public List<Operation> findAll() {
        return operationService.findAll();
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @RequestBody Operation operation) {
        operationService.update(id, operation);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        operationService.delete(id);
    }
}
