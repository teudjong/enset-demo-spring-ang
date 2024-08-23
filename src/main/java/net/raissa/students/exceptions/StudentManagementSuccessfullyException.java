package net.raissa.students.exceptions;

public class StudentManagementSuccessfullyException extends Exception{

    public StudentManagementSuccessfullyException(String message){
        super(message);
    }

    public StudentManagementSuccessfullyException(String message, Throwable cause){
        super(message,cause);
    }
}
