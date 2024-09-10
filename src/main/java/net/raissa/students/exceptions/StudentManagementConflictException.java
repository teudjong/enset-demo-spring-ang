package net.raissa.students.exceptions;

public class StudentManagementConflictException extends Exception{

    public StudentManagementConflictException(String message){
        super(message);
    }

    public StudentManagementConflictException(String message, Throwable cause){
        super(message,cause);
    }
}
