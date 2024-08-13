package net.raissa.students.repository;

import net.raissa.students.models.entities.Payment;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.models.entities.enums.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentCode(String paramString);

    List<Payment> findByStatus(PaymentStatus paramPaymentStatus);

    List<Payment> findByType(PaymentType paramPaymentType);
}
