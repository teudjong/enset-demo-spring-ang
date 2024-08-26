package net.raissa.students.exceptions;

import org.springframework.security.core.AuthenticationException;

public class StudentManagementUnauthorizedException extends AuthenticationException {

    public StudentManagementUnauthorizedException(String message){
        super(message);
    }

    public StudentManagementUnauthorizedException(String message,Throwable cause){
        super(message, cause);
    }
}
