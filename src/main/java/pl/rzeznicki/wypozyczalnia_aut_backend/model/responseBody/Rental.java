package pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Data;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RentalEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;

import java.util.List;
@Data
public class Rental {
    private Long id;
    private String city;
    private String address;
    private String phone;
    private String email;
    private byte[] photo;
    private List<User> moderators;
    private List<Car> cars;

    public Rental(RentalEntity rental){
        this.id = rental.getId();
        this.city = rental.getCity();
        this.address = rental.getAddress();
        this.phone = rental.getPhone();
        this.email = rental.getEmail();
        this.photo = rental.getPhoto();
        this.moderators = rental.getModerators().stream().map(User::new).toList();
        this.cars = rental.getCars().stream().map(Car::new).toList();
    }

    @Data
    public static class User {
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

    @Data
    public static class Car {
        private Long id;
        private String mark;
        private String model;
        private String color;
        private int year;
        private int price;
        private byte[] photo1;
        private byte[] photo2;
        private byte[] photo3;

        public Car(CarEntity carEntity){
            this.id = carEntity.getId();
            this.mark = carEntity.getMark();
            this.model = carEntity.getModel();
            this.color = carEntity.getColor();
            this.year = carEntity.getYear();
            this.price = carEntity.getPrice();
            this.photo1 = carEntity.getPhoto1();
            this.photo2 = carEntity.getPhoto2();
            this.photo3 = carEntity.getPhoto3();
        }
    }
}
