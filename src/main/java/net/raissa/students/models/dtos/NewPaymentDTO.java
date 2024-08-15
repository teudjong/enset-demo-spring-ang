package net.raissa.students.models.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.raissa.students.models.entities.enums.PaymentType;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPaymentDTO {
    @Schema(description = "Date de paiement de l'etudiant",format = "DD/MM/YY")
    private LocalDate date = LocalDate.now();
    @Schema(description = "Montant de paiement")
    private double amount;
    @Schema(description = "Type de paiement")
    private PaymentType type;
    @Schema(description = "Code de l'etudiant")
    private String studentCode;
}
