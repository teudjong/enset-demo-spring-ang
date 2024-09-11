package net.raissa.students.models.services;

import lombok.AllArgsConstructor;
import net.raissa.students.exceptions.StudentManagementNotFoundException;
import net.raissa.students.models.entities.AppRole;
import net.raissa.students.models.entities.AppUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
@AllArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser= null;
        try {
            appUser = accountService.loadUserByUsername(username);
        } catch (StudentManagementNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage(),e);
        }
        if (appUser==null)  throw new UsernameNotFoundException(String.format("User %s not found",username));

        String[] roles = appUser.getRoles().stream().map(AppRole::getRole).toArray(String[]::new);
        return User
                .withUsername(appUser.getUsername())
                .password(appUser.getPassword())
                .roles(roles).build();
    }
}
