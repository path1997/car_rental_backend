package pl.rzeznicki.wypozyczalnia_aut_backend.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "car")
public class CarEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mark;
    private String model;
    private String color;
    private int year;
    private int price;
    private Byte[] photo1;
    private Byte[] photo2;
    private Byte[] photo3;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rental_id")
    private RentalEntity rentalEntity;

    @OneToMany(mappedBy = "carEntity")
    private List<OrderEntity> orderEntities;
}
