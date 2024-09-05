package net.raissa.students.models.services;

import lombok.AllArgsConstructor;
import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;
import net.raissa.students.repository.AppRoleRepository;
import net.raissa.students.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if (appUser!=null) throw new RuntimeException("this user already exist");
        if (!password.equals(confirmPassword))  throw new RuntimeException("password not match");
        appUser=AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        return null;
    }

    @Override
    public void addRoleToUser(String username, String role) {

    }

    @Override
    public void removeRoleFromUser(String username, String role) {

    }
}
