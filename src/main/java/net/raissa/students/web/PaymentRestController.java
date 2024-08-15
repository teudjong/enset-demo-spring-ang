package net.raissa.students.web;


import lombok.extern.slf4j.Slf4j;
import net.raissa.students.models.dtos.NewPaymentDTO;
import net.raissa.students.models.entities.Payment;
import net.raissa.students.models.entities.Student;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.models.entities.enums.PaymentType;
import net.raissa.students.repository.PaymentRepository;
import net.raissa.students.repository.StudentRepository;
import net.raissa.students.services.PaymentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin({"*"})
@Slf4j
public class PaymentRestController {
    private final StudentRepository studentRepository;

    private final PaymentRepository paymentRepository;

    private final PaymentService paymentService;

    public PaymentRestController(StudentRepository studentRepository, PaymentRepository paymentRepository, PaymentService paymentService) {
        this.studentRepository = studentRepository;
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @GetMapping(path = {"/payments"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> allPayments() {
        return this.paymentRepository.findAll();
    }

    @GetMapping(path = {"/student/{code}/payments"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> paymentsByStudent(@PathVariable String code) {
        return this.paymentRepository.findByStudentCode(code);
    }

    @GetMapping(path = {"/payments/byStatus"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> paymentByStatus(@RequestParam PaymentStatus status) {
        return this.paymentRepository.findByStatus(status);
    }

    @GetMapping(path = {"/payments/byType/{type}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> paymentsByType(@PathVariable PaymentType type) {
        return this.paymentRepository.findByType(type);
    }

    @GetMapping(path = {"/payments/{id}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Payment getPaymentById(@PathVariable Long id) {
        return this.paymentRepository.findById(id).get();
    }

    @GetMapping({"/students"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Student> allStudents() {
        return this.studentRepository.findAll();
    }

    @GetMapping({"/students/{code}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Student getStudentByCode(@PathVariable String code) {
        return this.studentRepository.findByCode(code);
    }

    @GetMapping({"/students/{programId}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Student> getStudentsByProgramId(@PathVariable String programId) {
        return this.studentRepository.findByProgramId(programId);
    }

    @PutMapping({"/payments/{id}"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id) {
        try {
            return this.paymentService.updatePaymentStatus(status, id);
        }catch (Exception e){
            log.error(e.getMessage());
            return  new Payment();
        }
    }

    @PostMapping(path = {"/payments"}, consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment savePayment(@RequestParam("file") MultipartFile file, NewPaymentDTO newPaymentDTO) throws IOException {
        return this.paymentService.savePayment(file, newPaymentDTO);
    }

    @GetMapping(path = {"/payments/{id}/file"}, produces = {"application/pdf"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public byte[] getPaymentFile(@PathVariable Long id) throws IOException {
        return this.paymentService.getPaymentFile(id);
    }
}
