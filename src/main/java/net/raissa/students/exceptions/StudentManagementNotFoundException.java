package net.raissa.students.exceptions;

public class StudentManagementNotFoundException extends Exception{

    public StudentManagementNotFoundException(String message){
        super(message);
    }

    public StudentManagementNotFoundException(String message,Throwable cause){
        super(message,cause);
    }
}
