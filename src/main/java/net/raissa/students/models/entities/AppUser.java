package net.raissa.students.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.raissa.students.models.entities.AppRole;

import java.util.List;


@Entity
@Data  @NoArgsConstructor @AllArgsConstructor  @Builder
public class AppUser {
    @Id
    private String userId;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
