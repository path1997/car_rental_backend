package pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;

@Getter
@Setter
@Builder
public class AvailableCarListResponse {
    private String mark;
    private String model;
    private String color;
    private int year;
    private int price;
    private Byte[] photo1;
    private Byte[] photo2;
    private Byte[] photo3;


    public AvailableCarListResponse(CarEntity o) {
        this.mark = o.getMark();
        this.model = o.getModel();
        this.color = o.getColor();
        this.year = o.getYear();
        this.price = o.getPrice();
        this.photo1 = o.getPhoto1();
        this.photo2 = o.getPhoto2();
        this.photo3 = o.getPhoto3();
    }
}
