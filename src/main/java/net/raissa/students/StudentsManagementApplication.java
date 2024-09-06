package net.raissa.students;

import net.raissa.students.models.entities.Student;
import net.raissa.students.models.services.AccountService;
import net.raissa.students.repository.PaymentRepository;
import net.raissa.students.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@SpringBootApplication
public class StudentsManagementApplication {

	public static void main(String[] args) {

		SpringApplication.run(StudentsManagementApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository, PaymentRepository paymentRepository) {
		return args -> {
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("ebenezaire").code("112233").programId("SDIA").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("raissa").code("112244").programId("SDIA").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("chloe").code("112255").programId("GLSID").build());
			studentRepository.save(Student.builder().id(UUID.randomUUID().toString()).firstName("krishna").code("112266").programId("BDCC").build());
		};
	}

	@Bean
	CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
		return args -> {
			accountService.addNewRole("USER");
			accountService.addNewRole("ADMIN");
			accountService.addNewUser("user1","1234","user1@gmail.com","1234");
			accountService.addNewUser("user2","1234","user2@gmail.com","1234");
			accountService.addNewUser("admin","1234","admin@gmail.com","1234");

			accountService.addRoleToUser("user1","USER");
			accountService.addRoleToUser("user2","USER");
			accountService.addRoleToUser("admin","USER");
			accountService.addRoleToUser("admin","ADMIN");

		};
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
		daoAuthenticationProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(daoAuthenticationProvider);
	}

}
