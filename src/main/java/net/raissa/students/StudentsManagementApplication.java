package net.raissa.students;

import net.raissa.students.models.entities.Student;
import net.raissa.students.repository.PaymentRepository;
import net.raissa.students.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

}
