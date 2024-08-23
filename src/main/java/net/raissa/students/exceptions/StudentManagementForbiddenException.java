package net.raissa.students.exceptions;

public class StudentManagementForbiddenException extends Exception{

    public StudentManagementForbiddenException(String message){
        super(message);
    }

    public StudentManagementForbiddenException(String message,Throwable cause){
        super(message,cause);
    }
}
