package net.raissa.students.exceptions;

public class StudentManagementServerErrorException extends Exception{

    public StudentManagementServerErrorException(String message){
        super(message);
    }

    public StudentManagementServerErrorException(String message, Throwable cause){
        super(message, cause);
    }
}
