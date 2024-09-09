package net.raissa.students.models.services;

import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface AccountService {
    AppUser addNewUser(String username, String password, String email, String confirmPassword);

    AppRole addNewRole(String role);

    void addRoleToUser(String username, String role);

    void removeRoleFromUser(String username, String role);

    AppUser loadUserByUsername(String username);

    void deleteUser(Long id);

    User updateUser(Long id, User userDetails);

    User createUser(User user);

    User getUserById(Long id);

    List<User> getAllUsers();
}
