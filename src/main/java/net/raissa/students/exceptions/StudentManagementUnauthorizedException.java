package net.raissa.students.exceptions;

public class StudentManagementUnauthorizedException extends Exception{

    public StudentManagementUnauthorizedException(String message){
        super(message);
    }

    public StudentManagementUnauthorizedException(String message,Throwable cause){
        super(message, cause);
    }
}
