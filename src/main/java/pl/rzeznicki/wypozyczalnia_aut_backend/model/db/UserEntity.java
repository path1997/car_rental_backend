package pl.rzeznicki.wypozyczalnia_aut_backend.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;
    private String password;

    @OneToMany(mappedBy = "userEntity")
    private List<OrderEntity> orderEntities;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    public UserEntity(String firstName, String secondName, String phone, String email, String password) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }
}
