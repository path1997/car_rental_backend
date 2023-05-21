package pl.rzeznicki.wypozyczalnia_aut_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.rzeznicki.wypozyczalnia_aut_backend.exception.ServiceException;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.OrderEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RentalEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateCar;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateRental;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.UserOrder;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.*;

import java.util.*;

@Service
@AllArgsConstructor
public class MainService {
    private CarRepository carRepository;
    private OrderRepository orderRepository;
    private RentalRepository rentalRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;


    public List<CarEntity> getHomePage() {
        return carRepository.findAllByAvailableTrue();
    }

    public List<UserOrder> getUserOrders(Long id) {
        return orderRepository.getUserOrders(id).stream().map(UserOrder::new).toList();
    }

    public void rentCar(Long userId, Long carId) {
        Optional<UserEntity> userEntity = userRepository.findById(userId);
        Optional<CarEntity> carEntity = carRepository.findById(carId);
        if(userEntity.isEmpty()){
            throw new ServiceException(500, "User not found");
        }
        if(carEntity.isEmpty()){
            throw new ServiceException(500, "Car not found");
        }
        OrderEntity orderEntity = OrderEntity.builder()
                .userEntity(userEntity.get())
                .carEntity(carEntity.get())
                .active(true)
                .dateFrom(new Date())
                .dateTo(null)
                .build();
        orderRepository.save(orderEntity);
    }

    public void returnCar(Long orderId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if(orderEntity.isEmpty()){
            throw new ServiceException(500, "Order not found");
        }
        OrderEntity order = orderEntity.get();
        order.setActive(false);
        order.setDateTo(new Date());
        orderRepository.save(order);
    }

    public List<RentalEntity> getRentals() {
        return rentalRepository.findAll();
    }

    public void createRental(CreateRental createRental) {
        RentalEntity rental = RentalEntity.builder()
                .city(createRental.getCity())
                .address(createRental.getAddress())
                .phone(createRental.getPhone())
                .email(createRental.getEmail())
                .photo(createRental.getPhoto())
                .build();

        List<UserEntity> userEntities = new ArrayList<>();
        List<CarEntity> carEntities = new ArrayList<>();

        for(Long id : createRental.getCarsId()){
            Optional<CarEntity> carEntity = carRepository.findById(id);
            if(carEntity.isEmpty()){
                continue;
            }
            carEntities.add(carEntity.get());
        }
        for(Long id : createRental.getModeratorsId()){
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if(userEntity.isEmpty()){
                continue;
            }
            userEntities.add(userEntity.get());
        }
        rental.setCarEntity(carEntities);
        rental.setModerators(userEntities);
        rentalRepository.save(rental);
    }


    public void deleteRental(Long id) {
        Optional<RentalEntity> rental = rentalRepository.findById(id);
        if(rental.isEmpty()){
            throw new ServiceException(500, "Rental not found");
        }
        RentalEntity rentalEntity = rental.get();
        rentalEntity.setModerators(Collections.emptyList());
        rentalEntity.setCarEntity(Collections.emptyList());
        rentalRepository.save(rentalEntity);
        rentalRepository.delete(rentalEntity);
    }

    public void modifyRental(Long rentalId, CreateRental createRental) {
        Optional<RentalEntity> rentalEntityOptional = rentalRepository.findById(rentalId);
        if(rentalEntityOptional.isEmpty()){
            throw new ServiceException(500, "Rental not found");
        }
        RentalEntity rental = rentalEntityOptional.get();
        List<UserEntity> userEntities = new ArrayList<>();
        List<CarEntity> carEntities = new ArrayList<>();

        for(Long id : createRental.getCarsId()){
            Optional<CarEntity> carEntity = carRepository.findById(id);
            if(carEntity.isEmpty()){
                continue;
            }
            carEntities.add(carEntity.get());
        }
        for(Long id : createRental.getModeratorsId()){
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if(userEntity.isEmpty()){
                continue;
            }
            userEntities.add(userEntity.get());
        }
        rental.setCarEntity(carEntities);
        rental.setModerators(userEntities);

        rental.setCity(createRental.getCity());
        rental.setAddress(createRental.getAddress());
        rental.setEmail(createRental.getEmail());
        rental.setPhone(createRental.getPhone());
        rental.setPhoto(createRental.getPhoto());
        rentalRepository.save(rental);
    }

    public void createCar(CreateCar createCar) {
        CarEntity carEntity = CarEntity.builder()
                .mark(createCar.getMark())
                .model(createCar.getModel())
                .color(createCar.getColor())
                .year(createCar.getYear())
                .price(createCar.getPrice())
                .photo1(createCar.getPhoto1())
                .photo2(createCar.getPhoto2())
                .photo3(createCar.getPhoto3())
                .build();
        carRepository.save(carEntity);
    }

    public void modifyCar(Long id, CreateCar createCar){
       Optional<CarEntity> carEntity = carRepository.findById(id);
        if(carEntity.isEmpty()){
            throw new ServiceException(500, "Car not found");
        }
        CarEntity car = carEntity.get();
        car.setMark(createCar.getMark());
        car.setModel(createCar.getModel());
        car.setColor(createCar.getColor());
        car.setYear(createCar.getYear());
        car.setPrice(createCar.getPrice());
        car.setPhoto1(createCar.getPhoto1());
        car.setPhoto2(createCar.getPhoto2());
        car.setPhoto3(createCar.getPhoto3());
        carRepository.save(car);
    }

    public void deleteCar(Long id){
        carRepository.deleteById(id);
    }

}
