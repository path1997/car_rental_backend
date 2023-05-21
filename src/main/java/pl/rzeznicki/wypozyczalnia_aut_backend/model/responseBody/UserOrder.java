package pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody;

import lombok.Builder;
import lombok.Data;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.OrderEntity;

import java.util.Date;

@Data
@Builder
public class UserOrder {
    private CarEntity carEntity;
    private boolean active;
    private Date dateFrom;
    private Date dateTo;

    public UserOrder(OrderEntity orderEntity){
        this.carEntity = orderEntity.getCarEntity();
        this.active = orderEntity.isActive();
        this.dateFrom = orderEntity.getDateFrom();
        this.dateTo = orderEntity.getDateTo();
    }
}
