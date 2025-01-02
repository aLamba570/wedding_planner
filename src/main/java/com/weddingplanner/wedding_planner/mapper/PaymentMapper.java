package com.weddingplanner.wedding_planner.mapper;

import com.weddingplanner.wedding_planner.dto.PaymentDTO;
import com.weddingplanner.wedding_planner.model.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentDTO toDTO(Payment payment);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Payment toEntity(PaymentDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PaymentDTO dto, @MappingTarget Payment payment);

    // Enum mappings
    default Payment.PaymentStatus mapPaymentStatus(String status) {
        return status != null ? Payment.PaymentStatus.valueOf(status) : null;
    }

    default String mapPaymentStatusToString(Payment.PaymentStatus status) {
        return status != null ? status.name() : null;
    }

    default Payment.PaymentMethod mapPaymentMethod(String method) {
        return method != null ? Payment.PaymentMethod.valueOf(method) : null;
    }

    default String mapPaymentMethodToString(Payment.PaymentMethod method) {
        return method != null ? method.name() : null;
    }
}