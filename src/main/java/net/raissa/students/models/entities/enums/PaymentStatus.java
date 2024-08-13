package net.raissa.students.models.entities.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
    CREATED, VALIDATED, REJECTED
}
