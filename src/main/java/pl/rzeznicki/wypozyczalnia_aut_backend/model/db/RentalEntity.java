package pl.rzeznicki.wypozyczalnia_aut_backend.model.db;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
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
    private List<CarEntity> carEntity;
}
