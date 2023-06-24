package pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody;

import lombok.Data;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.OrderEntity;

import java.util.Date;

@Data
public class Order {
    private Long id;
    private Rental.Car car;
    private boolean active;
    private Date dateFrom;
    private Date dateTo;

    public Order(OrderEntity orderEntity){
        this.id = orderEntity.getId();
        this.car = new Rental.Car(orderEntity.getCarEntity());
        this.active = orderEntity.isActive();
        this.dateFrom = orderEntity.getDateFrom();
        this.dateTo = orderEntity.getDateTo();
    }
}
