package net.raissa.students.models.services;

import net.raissa.students.exceptions.StudentManagementConflictException;
import net.raissa.students.exceptions.StudentManagementNotFoundException;
import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;
import net.raissa.students.repository.AppRoleRepository;
import net.raissa.students.repository.AppUserRepository;
import net.raissa.students.services.AccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private AppRoleRepository appRoleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddNewUserSuccess() {
        // Arrange
        String username = "user1";
        String password = "password";
        String email = "user1@example.com";
        String confirmPassword = "password";

        when(appUserRepository.findByUsername(username)).thenReturn(null);
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(appUserRepository.save(any(AppUser.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        AppUser newUser = accountService.addNewUser(username, password, email, confirmPassword);

        // Assert
        assertNotNull(newUser);
        assertEquals(username, newUser.getUsername());
        assertEquals("encodedPassword", newUser.getPassword());
        verify(appUserRepository).save(newUser);
    }

    @Test
    void testAddNewUserUserExists() {
        // Arrange
        String username = "user1";
        String password = "password";
        String email = "user1@example.com";
        String confirmPassword = "password";

        when(appUserRepository.findByUsername(username)).thenReturn(new AppUser());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class,() -> accountService.addNewUser(username, password, email, confirmPassword));
        assertEquals("this user already exist", exception.getMessage());
    }

    @Test
    void testAddNewUserPasswordMismatch() {
        // Arrange
        String username = "user1";
        String password = "password";
        String email = "user1@example.com";
        String confirmPassword = "differentPassword";

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> accountService.addNewUser(username, password, email, confirmPassword));
        assertEquals("password not match", exception.getMessage());
    }

    @Test
    void testAddNewRoleSuccess() throws StudentManagementConflictException {
        // Arrange
        String role = "ROLE_USER";

        when(appRoleRepository.findById(role)).thenReturn(Optional.empty());
        when(appRoleRepository.save(any(AppRole.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        AppRole newRole = accountService.addNewRole(role);

        // Assert
        assertNotNull(newRole);
        assertEquals(role, newRole.getRole());
        verify(appRoleRepository).save(newRole);
    }

    @Test
    void testAddNewRoleRoleExists() {
        // Arrange
        String role = "ROLE_USER";
        when(appRoleRepository.findById(role)).thenReturn(Optional.of(new AppRole()));

        // Act & Assert
        StudentManagementConflictException exception = assertThrows(StudentManagementConflictException.class,() -> accountService.addNewRole(role));
        assertEquals("Le role ROLE_USER existe deja", exception.getMessage());
    }

    @Test
    void testAddRoleToUserUserNotFound() {
        // Arrange
        String username = "user1";
        String role = "ROLE_USER";
        when(appUserRepository.findByUsername(username)).thenReturn(null);

        // Act & Assert
        StudentManagementNotFoundException exception = assertThrows(StudentManagementNotFoundException.class,
                () -> accountService.addRoleToUser(username, role));
        assertEquals("L'utilisateur user1 n'existe pas; veuillez le creer", exception.getMessage());
    }

    @Test
    void testAddRoleToUserRoleNotFound() {
        // Arrange
        String username = "user1";
        String role = "ROLE_USER";
        AppUser user = new AppUser();
        when(appUserRepository.findByUsername(username)).thenReturn(user);
        when(appRoleRepository.findById(role)).thenReturn(Optional.empty());

        // Act & Assert
        StudentManagementNotFoundException exception = assertThrows(StudentManagementNotFoundException.class,
                () -> accountService.addRoleToUser(username, role));
        assertEquals("Le role ROLE_USER n'existe pas; veuillez le creer", exception.getMessage());
    }

    @Test
    void testLoadUserByUsernameUserExists() throws StudentManagementNotFoundException {
        // Arrange
        String username = "user1";
        AppUser user = new AppUser();
        when(appUserRepository.findByUsername(username)).thenReturn(user);

        // Act
        AppUser foundUser = accountService.loadUserByUsername(username);

        // Assert
        assertEquals(user, foundUser);
    }

    @Test
    void testLoadUserByUsernameUserNotFound() {
        // Arrange
        String username = "user1";
        when(appUserRepository.findByUsername(username)).thenReturn(null);

        // Act & Assert
        StudentManagementNotFoundException exception = assertThrows(StudentManagementNotFoundException.class,
                () -> accountService.loadUserByUsername(username));
        assertEquals("L'utilisateur user1 n'existe pas; veuillez le cree", exception.getMessage());
    }
}
