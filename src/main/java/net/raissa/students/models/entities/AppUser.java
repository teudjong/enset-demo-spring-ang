package net.raissa.students.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Entity
@Data  @NoArgsConstructor @AllArgsConstructor  @Builder
public class AppUser {
    @Id
    private String userId;

    @Column(unique = true)
    private String username;

    private String password;

    private Date createDate = new Date();

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;
}
