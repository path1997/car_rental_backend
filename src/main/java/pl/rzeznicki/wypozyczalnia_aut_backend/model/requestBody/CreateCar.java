package pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody;

import lombok.Data;

@Data
public class CreateCar {
    private String mark;
    private String model;
    private String color;
    private int year;
    private int price;
    private byte[] photo1;
    private byte[] photo2;
    private byte[] photo3;
}
