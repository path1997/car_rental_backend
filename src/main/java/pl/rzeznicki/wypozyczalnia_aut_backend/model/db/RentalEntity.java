package pl.rzeznicki.wypozyczalnia_aut_backend.model.db;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rental")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String city;
    private String address;
    private String phone;
    private String email;
    private Byte[] photo;
    @OneToMany(mappedBy = "rentalEntity")
    private List<UserEntity> moderators;

    @OneToMany(mappedBy = "rentalEntity")
    private List<CarEntity> carEntity;
}
