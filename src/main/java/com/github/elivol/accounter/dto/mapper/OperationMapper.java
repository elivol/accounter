package com.github.elivol.accounter.dto.mapper;

import com.github.elivol.accounter.dto.model.OperationDto;
import com.github.elivol.accounter.model.Operation;

public class OperationMapper {

    public static OperationDto toOperationDto(Operation operation) {
        return new OperationDto()
                .setId(operation.getId())
                .setCreatedAt(operation.getCreatedAt())
                .setAmount(operation.getAmount())
                .setIsIncoming(operation.getIsIncoming())
                .setAccountId(operation.getAccount().getId());
    }
}
