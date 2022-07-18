package com.github.elivol.accounter.controller.api;

import com.github.elivol.accounter.model.Operation;
import com.github.elivol.accounter.hateoas.assembler.OperationModelAssembler;
import com.github.elivol.accounter.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(path = "/me/operations")
@AllArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final OperationModelAssembler operationModelAssembler;

    @GetMapping
    public CollectionModel<EntityModel<Operation>> findAll() {

        List<EntityModel<Operation>> operations = operationService.findAll()
                .stream()
                .map(operationModelAssembler::toModel)
                .toList();

        return CollectionModel.of(
                operations,
                linkTo(methodOn(OperationController.class).findAll()).withSelfRel()
        );
    }

    @PutMapping(path = "/{id}")
    public void update(@PathVariable Long id, @Valid @RequestBody Operation operation) {
        operationService.update(id, operation);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        operationService.delete(id);
    }
}
