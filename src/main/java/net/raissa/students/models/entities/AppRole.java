package net.raissa.students.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor  @AllArgsConstructor  @Builder
public class AppRole {

    @Id
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppRole appRole = (AppRole) o;
        return role != null && Objects.equals(role, appRole.role);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
