package pl.rzeznicki.wypozyczalnia_aut_backend.model.db;

import jakarta.persistence.*;
import lombok.*;

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
    @Column(name = "photo", columnDefinition = "LONGBLOB")
    private byte[] photo;
    @OneToMany(mappedBy = "rental")
    private List<UserEntity> moderators;

    @OneToMany(mappedBy = "rentalEntity")
    private List<CarEntity> cars;
}
