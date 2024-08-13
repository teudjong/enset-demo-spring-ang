package net.raissa.students.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Student {
    @Id
    private String id;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String code;

    private String programId;

    private String photo;
}
