package net.raissa.students.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import net.raissa.students.models.dtos.ApiErrorResponse;
import net.raissa.students.models.entities.Student;
import net.raissa.students.services.StudentService;
import net.raissa.students.repository.StudentRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@Slf4j
public class StudentController {

    private final StudentRepository studentRepository;

    private final StudentService studentService;


    public StudentController(StudentRepository studentRepository, StudentService studentService) {
        this.studentRepository = studentRepository;
        this.studentService = studentService;
    }


    @Operation(summary = "View a list of available students",  description = "Retrieve a list of all available students")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved list",content = {@Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Student.class)))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping({"/students"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    public List<Student> allStudents() {
        return this.studentRepository.findAll();
    }



    @Operation(summary = "Get a student by code", description = "Retrieve a specific student using their code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved the student",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = Student.class))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping({"/students/{code}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public Student getStudentByCode(@PathVariable String code) {
        return this.studentRepository.findByCode(code);
    }



    @Operation(summary = "Get students by program ID", description = "Retrieve a list of students based on the provided program ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "Successfully retrieved list of students",content = {@Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Student.class)))}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "401",description = "You are not authorized to view the resource",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "403",description = "Accessing the resource you were trying to reach is forbidden",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "404",description = "The resource you were trying to reach is not found",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))}),
            @ApiResponse(responseCode = "500",description = "Server error",content = {@Content(mediaType = "application/json",schema = @Schema(implementation = ApiErrorResponse.class))})
    })
    @GetMapping({"/students/{programId}"})
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    public List<Student> getStudentsByProgramId(@PathVariable String programId) {
        return this.studentRepository.findByProgramId(programId);
    }

}
