package pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody;

import jakarta.persistence.Column;
import lombok.Data;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RentalEntity;

import java.util.List;

@Data
public class Car {
    private Long id;
    private String mark;
    private String model;
    private String color;
    private int year;
    private int price;
    private byte[] photo1;
    private byte[] photo2;
    private byte[] photo3;
    private Rental rental;

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
        this.rental = new Rental(carEntity.getRentalEntity());
    }

    @Data
    public static class Rental {
        private Long id;
        private String city;
        private String address;
        private String phone;
        private String email;
        private byte[] photo;

        public Rental(RentalEntity rental) {
            this.id = rental.getId();
            this.city = rental.getCity();
            this.address = rental.getAddress();
            this.phone = rental.getPhone();
            this.email = rental.getEmail();
            this.photo = rental.getPhoto();
        }
    }
}
