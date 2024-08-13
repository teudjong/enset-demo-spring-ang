package net.raissa.students.repository;

import net.raissa.students.models.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByCode(String paramString);

    List<Student> findByProgramId(String paramString);
}
