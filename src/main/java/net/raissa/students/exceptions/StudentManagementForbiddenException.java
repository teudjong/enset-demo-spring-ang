package net.raissa.students.exceptions;

import org.springframework.security.access.AccessDeniedException;

public class StudentManagementForbiddenException extends AccessDeniedException {

    public StudentManagementForbiddenException(String message){
        super(message);
    }

    public StudentManagementForbiddenException(String message,Throwable cause){
        super(message,cause);
    }
}
