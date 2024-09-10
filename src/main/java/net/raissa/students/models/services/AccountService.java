package net.raissa.students.models.services;

import net.raissa.students.exceptions.StudentManagementConflictException;
import net.raissa.students.exceptions.StudentManagementNotFoundException;
import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;

    public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);

    AppRole addNewRole(String role) throws StudentManagementConflictException;

    void addRoleToUser(String username, String role) throws StudentManagementNotFoundException;

    boolean removeRoleFromUser(String username, String role) throws StudentManagementNotFoundException;

    AppUser loadUserByUsername(String username);
}
