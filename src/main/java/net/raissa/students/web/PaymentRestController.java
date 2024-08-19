package net.raissa.students.web;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.raissa.students.models.dtos.NewPaymentDTO;
import net.raissa.students.models.entities.Payment;
import net.raissa.students.models.entities.Student;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.models.entities.enums.PaymentType;
import net.raissa.students.repository.PaymentRepository;
import net.raissa.students.repository.StudentRepository;
import net.raissa.students.services.PaymentService;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
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

    @ApiOperation(value = "View a list of all payments", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of payments"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = {"/payments"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> allPayments() {
        return this.paymentRepository.findAll();
    }


    @ApiOperation(value = "Get payments by student code", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of payments"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = {"/student/{code}/payments"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> paymentsByStudent( @ApiParam(value = "Code of the student to retrieve payments for", required = true, example = "12345") @PathVariable String code ) {
        return this.paymentRepository.findByStudentCode(code);
    }


    @ApiOperation(value = "Get payments by status", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of payments"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = {"/payments/byStatus"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> paymentByStatus( @ApiParam(value = "Status of the payments to retrieve", required = true, example = "COMPLETED") @RequestParam PaymentStatus status) {
        return this.paymentRepository.findByStatus(status);
    }


    @ApiOperation(value = "Get payments by type", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of payments"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = {"/payments/byType/{type}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Payment> paymentsByType( @ApiParam(value = "Type of the payments to retrieve", required = true, example = "CREDIT_CARD") @PathVariable PaymentType type) {
        return this.paymentRepository.findByType(type);
    }

    @ApiOperation(value = "Get a payment by ID", response = Payment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the payment"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping(path = {"/payments/{id}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Payment getPaymentById(@ApiParam(value = "ID of the payment to retrieve", required = true, example = "1") @PathVariable Long id) {
        return this.paymentRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Payment not found"));
    }


    @ApiOperation(value = "View a list of available students", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping({"/students"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Student> allStudents() {
        return this.studentRepository.findAll();
    }


    @ApiOperation(value = "Get a student by code", response = Student.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the student"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping({"/students/{code}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Student getStudentByCode( @ApiParam(value = "Code of the student to retrieve", required = true, example = "S12345") @PathVariable String code) {
        return this.studentRepository.findByCode(code);
    }

    @ApiOperation(value = "Get students by program ID", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of students"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @GetMapping({"/students/{programId}"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Student> getStudentsByProgramId( @ApiParam(value = "ID of the program to retrieve students for", required = true, example = "P2024") @PathVariable String programId) {
        return this.studentRepository.findByProgramId(programId);
    }


    @ApiOperation(value = "Update the status of a payment", response = Payment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the payment status"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PutMapping({"/payments/{id}"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment updatePaymentStatus( @ApiParam(value = "ID of the payment to update", required = true, example = "123") @RequestParam PaymentStatus status, @ApiParam(value = "ID of the payment to update", required = true, example = "123") @PathVariable Long id) {
        try {
            return this.paymentService.updatePaymentStatus(status, id);
        }catch (Exception e){
            log.error(e.getMessage());
            return  new Payment();
        }
    }

    @ApiOperation(value = "Save a payment with a file and payment details", response = Payment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully saved the payment"),
            @ApiResponse(code = 400, message = "Invalid request parameters or file format"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(path = {"/payments"}, consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public Payment savePayment( @ApiParam(value = "File associated with the payment", required = true) @RequestParam("file") MultipartFile file, @ApiParam(value = "Payment details", required = true)
            NewPaymentDTO newPaymentDTO) throws IOException {
        return this.paymentService.savePayment(file, newPaymentDTO);
    }

    @ApiOperation(value = "Get the file associated with a payment", produces = "application/pdf")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the file"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The payment or file you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(path = {"/payments/{id}/file"}, produces = {"application/pdf"})
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public byte[] getPaymentFile( @ApiParam(value = "ID of the payment to retrieve the file for", required = true, example = "123") @PathVariable Long id) throws IOException {
        return this.paymentService.getPaymentFile(id);
    }
}
