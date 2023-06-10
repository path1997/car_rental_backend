package pl.rzeznicki.wypozyczalnia_aut_backend.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "photo1", columnDefinition = "LONGBLOB")
    private byte[] photo1;
    @Column(name = "photo2", columnDefinition = "LONGBLOB")
    private byte[] photo2;
    @Column(name = "photo3", columnDefinition = "LONGBLOB")
    private byte[] photo3;
    boolean available;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rental_id")
    private RentalEntity rentalEntity;

    @OneToMany(mappedBy = "carEntity")
    private List<OrderEntity> orderEntities;
}
