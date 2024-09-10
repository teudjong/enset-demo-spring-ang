package net.raissa.students.models.services;

import lombok.AllArgsConstructor;
import net.raissa.students.exceptions.StudentManagementConflictException;
import net.raissa.students.exceptions.StudentManagementNotFoundException;
import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;
import net.raissa.students.repository.AppRoleRepository;
import net.raissa.students.repository.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
        return appUserRepository.save(appUser);
    }

    @Override
    public AppRole addNewRole(String role) throws StudentManagementConflictException {
        AppRole appRole=appRoleRepository.findById(role).orElse(null);
        if(appRole!=null)  throw new StudentManagementConflictException(String.format("Le role %s existe deja",role));
        appRole=AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) throws StudentManagementNotFoundException {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(Objects.nonNull(appUser)){
            Optional<AppRole> appRole= appRoleRepository.findById(role);
            if (appRole.isPresent()){
                appUser.getRoles().add(appRole.get());
            }else {
                throw new  StudentManagementNotFoundException(String.format("Le role %s n'existe pas; veuillez le cree",role));
            }
        }else {
            throw new  StudentManagementNotFoundException(String.format("L'utilisateur %s n'existe pas; veuillez le cree",username));
        }

    }

    @Override
    public boolean removeRoleFromUser(String username, String role) throws StudentManagementNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username);


        Optional<AppRole> appRole;
        if (Objects.nonNull(appUser)) {
            appRole = appRoleRepository.findById(role);

            boolean removeRole;
            if (appRole.isPresent()) {
                appUser.getRoles().remove(appRole.get());
                List<AppRole> roles = appUser.getRoles();


            if (removeRoleFromUser(username,role)) {
                removeRoleFromUser(username, role) = roles.remove(appRole);

            } else{
                    throw new StudentManagementNotFoundException(String.format("ajouter Le role a l'utilisateur %s ; veuillez le cree", role, username));
                   }
            } else{
                    throw new StudentManagementNotFoundException(String.format("Le role %s n'existe pas; veuillez le cree", role));
                  }
            }else{
                throw new StudentManagementNotFoundException(String.format("L'utilisateur %s n'existe pas; veuillez le cree", username));
            }
        return false;
    }


    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}
