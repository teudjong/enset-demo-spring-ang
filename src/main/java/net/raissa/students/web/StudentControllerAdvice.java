package net.raissa.students.web;

import net.raissa.students.exceptions.*;
import net.raissa.students.models.dtos.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class StudentControllerAdvice {

    @ExceptionHandler(StudentManagementNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentManagementNotFoundException(StudentManagementNotFoundException e) {
        ApiErrorResponse apiErrorResponse=new ApiErrorResponse();
        apiErrorResponse.setMessage(e.getMessage());
        apiErrorResponse.setCode(String.valueOf(HttpStatus.NOT_FOUND.value()));
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentManagementForbiddenException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentManagementForbiddenException(StudentManagementForbiddenException e) {
        ApiErrorResponse apiErrorResponse=new ApiErrorResponse();
        apiErrorResponse.setMessage(e.getMessage());
        apiErrorResponse.setCode(String.valueOf(HttpStatus.FORBIDDEN.value()));
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(StudentManagementServerErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentManagementServerErrorException(StudentManagementServerErrorException e) {
        ApiErrorResponse apiErrorResponse=new ApiErrorResponse();
        apiErrorResponse.setMessage(e.getMessage());
        apiErrorResponse.setCode(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(StudentManagementBadRequestException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentManagementBadRequestException(StudentManagementBadRequestException e) {
        ApiErrorResponse apiErrorResponse =new ApiErrorResponse();
        apiErrorResponse.setMessage(e.getMessage());
        apiErrorResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StudentManagementSuccessfullyException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentManagementSuccessfullyException(StudentManagementSuccessfullyException e) {
        ApiErrorResponse apiErrorResponse=new ApiErrorResponse();
        apiErrorResponse.setMessage(e.getMessage());
        apiErrorResponse.setCode(String.valueOf(HttpStatus.OK.value()));
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.OK);
    }

    @ExceptionHandler(StudentManagementUnauthorizedException.class)
    public ResponseEntity<ApiErrorResponse> handleStudentManagementUnauthorizedException(StudentManagementUnauthorizedException e) {
        ApiErrorResponse apiErrorResponse=new ApiErrorResponse();
        apiErrorResponse.setMessage(e.getMessage());
        apiErrorResponse.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        return new ResponseEntity<>(apiErrorResponse,HttpStatus.UNAUTHORIZED);
    }
}
