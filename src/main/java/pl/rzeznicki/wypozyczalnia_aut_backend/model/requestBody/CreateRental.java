package pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody;

import jakarta.persistence.OneToMany;
import lombok.Data;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;

import java.util.List;

@Data
public class CreateRental {
    private String city;
    private String address;
    private String phone;
    private String email;
    private byte[] photo;
    private List<Long> moderatorsId;
    private List<Long> carsId;
}
