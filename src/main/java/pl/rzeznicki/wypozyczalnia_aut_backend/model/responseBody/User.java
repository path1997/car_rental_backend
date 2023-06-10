package pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody;

import lombok.Data;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;

@Data
public class User {
    private Long id;
    private String firstName;
    private String secondName;
    private String phone;
    private String email;

    public User(UserEntity userEntity){
        this.id = userEntity.getId();
        this.firstName = userEntity.getFirstName();
        this.secondName = userEntity.getSecondName();
        this.phone = userEntity.getPhone();
        this.email = userEntity.getEmail();
    }
}
