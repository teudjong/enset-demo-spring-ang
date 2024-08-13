package net.raissa.students.models.entities;

import jakarta.persistence.*;
import lombok.*;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.models.entities.enums.PaymentType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Payment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private LocalDate date;

    private double amount;

    private PaymentType type;

    private PaymentStatus status;

    private String file;

    @ManyToOne
    private Student student;


}
