package net.raissa.students.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.raissa.students.exceptions.StudentManagementNotFoundException;
import net.raissa.students.models.dtos.ApiErrorResponse;
import net.raissa.students.models.dtos.NewPaymentDTO;
import net.raissa.students.models.entities.Payment;
import net.raissa.students.models.entities.enums.PaymentStatus;
import net.raissa.students.models.entities.enums.PaymentType;
import net.raissa.students.models.services.PaymentService;
import net.raissa.students.repository.PaymentRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin({"*"})
@Slf4j
public class PaymentRestController {

    private final PaymentRepository paymentRepository;

    private final PaymentService paymentService;


    public PaymentRestController(PaymentRepository paymentRepository, PaymentService paymentService) {
        this.paymentRepository = paymentRepository;
        this.paymentService = paymentService;
    }

    @Operation(summary = "View a list of all payments", description = "View a list of all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved list of payments",content = {@Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Payment.class)))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/payments"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<Payment> allPayments() {
        return this.paymentRepository.findAll();
    }


    @Operation(summary = "Get payments by student code", description = "Retrieve a list of payments associated with a specific student code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved list of payments",content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Payment.class)))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/student/{code}/payments"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<Payment> paymentsByStudent(@PathVariable String code ) {
        return this.paymentRepository.findByStudentCode(code);
    }


    @Operation(summary = "Get payments by status", description = "Retrieve a list of payments based on their status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved list of payments",content = {@Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Payment.class)))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/payments/byStatus"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<Payment> paymentByStatus(@RequestParam PaymentStatus status) {
        return this.paymentRepository.findByStatus(status);
    }


    @Operation(summary = "Get payments by type",description = "Retrieve a list of payments based on their type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved list of payments",content = {@Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Payment.class)))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/payments/byType/{type}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<Payment> paymentsByType(@PathVariable PaymentType type) {
        return this.paymentRepository.findByType(type);
    }

    @Operation(summary = "Get a payment by ID", description = "Retrieve a specific payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved the payment",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Payment.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/payments/{id}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public Payment getPaymentById(@PathVariable Long id) {
        return this.paymentRepository.findById(id).orElseThrow(() -> new OpenApiResourceNotFoundException("Payment not found"));
    }


    @Operation(summary = "Update the status of a payment", description = "Update the status of a specific payment using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully updated the payment status",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Payment.class))}),
            @ApiResponse(responseCode = "400",description = "Invalid request parameters",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PutMapping({"/payments/{id}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public ResponseEntity<?> updatePaymentStatus(@RequestParam PaymentStatus status, @PathVariable Long id) throws StudentManagementNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.updatePaymentStatus(status, id));
    }


    @Operation(summary = "Save a payment with a file and payment details", description = "Save a new payment by providing payment details and an associated file")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "Successfully saved the payment",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Payment.class))}),
            @ApiResponse(responseCode = "400",description = "Invalid request parameters or file format",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @PostMapping(path = {"/payments"}, consumes = {"multipart/form-data"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public Payment savePayment(@RequestParam("file") MultipartFile file, @RequestBody @Valid
            NewPaymentDTO newPaymentDTO) throws IOException {
        return this.paymentService.savePayment(file, newPaymentDTO);
    }


    @Operation(summary = "Get the file associated with a payment",description = "Retrieve the file associated with a specific payment using its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved the file",content = {@Content(mediaType = "application/pdf")}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The payment or file you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Internal server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping(path = {"/payments/{id}/file"}, produces = {"application/pdf"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public byte[] getPaymentFile(@PathVariable() Long id) throws IOException {
        return this.paymentService.getPaymentFile(id);
    }

}
