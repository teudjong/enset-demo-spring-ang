package net.raissa.students.models.services;

import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFromUser(String username, String role);
}
