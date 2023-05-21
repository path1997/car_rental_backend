package pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody;

import lombok.Data;

@Data
public class CreateCar {
    private String mark;
    private String model;
    private String color;
    private int year;
    private int price;
    private Byte[] photo1;
    private Byte[] photo2;
    private Byte[] photo3;
}
