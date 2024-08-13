package net.raissa.students.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.models.entities.enums.PaymentType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;

    private LocalDate date;

    private double amount;

    private PaymentType type;

    private PaymentStatus status;
}
