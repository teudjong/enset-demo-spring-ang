package net.raissa.students.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Identifiant du paiement")
    private Long id;
    @Schema(description = "Date de paiement de l'etudiant")
    private LocalDate date;
    @Schema(description = "Montant de paiement")
    private double amount;
    @Schema(description = "Type de paiement")
    private PaymentType type;
    @Schema(description = "Status du paiement")
    private PaymentStatus status;
}
