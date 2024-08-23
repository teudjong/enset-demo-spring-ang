package net.raissa.students.exceptions;

public class StudentManagementBadRequestException extends Exception{

    public StudentManagementBadRequestException(String message){
        super(message);
    }

    public StudentManagementBadRequestException(String message, Throwable cause){
        super(message,cause);
    }
}
