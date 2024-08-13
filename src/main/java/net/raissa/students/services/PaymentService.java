package net.raissa.students.services;

import net.raissa.students.models.dtos.NewPaymentDTO;
import net.raissa.students.models.entities.Payment;
import net.raissa.students.models.entities.Student;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.repository.PaymentRepository;
import net.raissa.students.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {
    private final StudentRepository studentRepository;

    private final PaymentRepository paymentRepository;

    public PaymentService(StudentRepository studentRepository, PaymentRepository paymentRepository) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payment savePayment(MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        Path folderpath = Paths.get(System.getProperty("user.home"), "enset-data", "payments");
        if (!Files.exists(folderpath))
            Files.createDirectories(folderpath);
        String fileName = UUID.randomUUID().toString();
        Path filePath = Paths.get(System.getProperty("user.home"), "enset-data", "payment", fileName + ".pdf");
        Files.copy(file.getInputStream(), filePath);
        Student student = this.studentRepository.findByCode(newPaymentDTO.getStudentCode());
        Payment payment = Payment.builder().date(newPaymentDTO.getDate()).type(newPaymentDTO.getType()).student(student).amount(newPaymentDTO.getAmount()).file(filePath.toUri().toString()).status(PaymentStatus.CREATED).build();
        return this.paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(PaymentStatus status, Long id) throws Exception {
        Optional<Payment> paymentOptional = this.paymentRepository.findById(id);
        Payment payment ;
        if (paymentOptional.isPresent()){
            payment = paymentOptional.get();
            payment.setStatus(status);
        }else {
            throw new Exception("Aucun objet trouve avec l'ID "+ id);
        }

        return this.paymentRepository.save(payment);
    }

    public byte[] getPaymentFile(Long paymentId) throws IOException {
        Optional<Payment> paymentOptional = this.paymentRepository.findById(paymentId);
        Payment payment = new Payment();
        if (paymentOptional.isPresent()){
            payment = paymentOptional.get();
        }
        return Files.readAllBytes(Path.of(URI.create(payment.getFile())));
    }
}
