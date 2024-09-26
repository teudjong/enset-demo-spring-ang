package net.raissa.students.models.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Date;
import java.util.List;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor @AllArgsConstructor  @Builder
public class AppUser {
    @Id
    private String userId;

    @Column(unique = true)
    private String username;

    private String password;

    private Date createDate;

    @Column(unique = true)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AppUser appUser = (AppUser) o;
        return userId != null && Objects.equals(userId, appUser.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
