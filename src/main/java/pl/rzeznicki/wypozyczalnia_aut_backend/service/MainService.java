package pl.rzeznicki.wypozyczalnia_aut_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.rzeznicki.wypozyczalnia_aut_backend.exception.ServiceException;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.OrderEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RentalEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateCar;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateRental;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.*;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.*;
import pl.rzeznicki.wypozyczalnia_aut_backend.security.model.UserDetailsImpl;

import java.io.IOException;
import java.util.*;

@Service
@AllArgsConstructor
public class MainService {
    private CarRepository carRepository;
    private OrderRepository orderRepository;
    private RentalRepository rentalRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;


    public List<Car> getHomePage() {
        return carRepository.findAllByAvailableTrue().stream().map(Car::new).toList();
    }

    public List<Order> getUserOrders() {
        System.out.println(SecurityContextHolder.getContext());
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return orderRepository.getUserOrders(userDetails.getId()).stream().map(Order::new).toList();
    }

    public void rentCar(Long carId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<UserEntity> userEntity = userRepository.findById(userDetails.getId());
        Optional<CarEntity> carEntity = carRepository.findById(carId);
        if (userEntity.isEmpty()) {
            throw new ServiceException(500, "User not found");
        }
        if (carEntity.isEmpty()) {
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

    public void returnCar(Long orderId, Long rentalId) {
        Optional<OrderEntity> orderEntity = orderRepository.findById(orderId);
        if (orderEntity.isEmpty()) {
            throw new ServiceException(500, "Order not found");
        }
        OrderEntity order = orderEntity.get();
        order.setActive(false);
        order.setDateTo(new Date());
        orderRepository.save(order);
        CarEntity car = orderEntity.get().getCarEntity();
        Optional<RentalEntity> rentalOptional = rentalRepository.findById(rentalId);
        if(rentalOptional.isEmpty()){
            throw new ServiceException(500, "Rental not found");
        }
        car.setRentalEntity(rentalOptional.get());
        carRepository.save(car);
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll().stream().map(Rental::new).toList();
    }

    public void createRental(String city, String address, String phone, String email, MultipartFile photo) {
        RentalEntity rental = null;
        try {
            rental = RentalEntity.builder()
                    .city(city)
                    .address(address)
                    .phone(phone)
                    .email(email)
                    .photo(photo.getBytes())
                    .build();
        } catch (IOException e) {
            throw new ServiceException(500, "Error when convert photo to byte array");
        }

        /*List<UserEntity> userEntities = new ArrayList<>();
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
        rental.setModerators(userEntities);*/
        rentalRepository.save(rental);
    }


    public void deleteRental(Long id) {
        Optional<RentalEntity> rental = rentalRepository.findById(id);
        if (rental.isEmpty()) {
            throw new ServiceException(500, "Rental not found");
        }
        RentalEntity rentalEntity = rental.get();
        rentalEntity.setModerators(Collections.emptyList());
        rentalEntity.setCars(Collections.emptyList());
        rentalRepository.save(rentalEntity);
        rentalRepository.delete(rentalEntity);
    }

    public void modifyRental(Long rentalId, CreateRental createRental) {
        Optional<RentalEntity> rentalEntityOptional = rentalRepository.findById(rentalId);
        if (rentalEntityOptional.isEmpty()) {
            throw new ServiceException(500, "Rental not found");
        }
        RentalEntity rental = rentalEntityOptional.get();
        List<UserEntity> userEntities = new ArrayList<>();
        List<CarEntity> carEntities = new ArrayList<>();

        for (Long id : createRental.getCarsId()) {
            Optional<CarEntity> carEntity = carRepository.findById(id);
            if (carEntity.isEmpty()) {
                continue;
            }
            carEntities.add(carEntity.get());
        }
        for (Long id : createRental.getModeratorsId()) {
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if (userEntity.isEmpty()) {
                continue;
            }
            userEntities.add(userEntity.get());
        }
        rental.setCars(carEntities);
        rental.setModerators(userEntities);

        rental.setCity(createRental.getCity());
        rental.setAddress(createRental.getAddress());
        rental.setEmail(createRental.getEmail());
        rental.setPhone(createRental.getPhone());
        rental.setPhoto(createRental.getPhoto());
        rentalRepository.save(rental);
    }

    public void createCar(String mark, String model, String color, int year, int price, long rentalId, MultipartFile photo1, MultipartFile photo2, MultipartFile photo3) {
        try {
            Optional<RentalEntity> rental = rentalRepository.findById(rentalId);
            RentalEntity rentalEntity = null;
            if (rental.isPresent()) {
                rentalEntity = rental.get();
            }
            CarEntity carEntity = CarEntity.builder()
                    .mark(mark)
                    .model(model)
                    .color(color)
                    .year(year)
                    .price(price)
                    .photo1(photo1.getBytes())
                    .photo2(photo2 != null ? photo2.getBytes() : null)
                    .photo3(photo3 != null ? photo3.getBytes() : null)
                    .rentalEntity(rentalEntity)
                    .available(true)
                    .build();
            carRepository.save(carEntity);
        } catch (IOException e) {
            throw new ServiceException(500, "Error when convert photo to byte array");
        }
    }

    public void modifyCar(Long id, String mark, String model, String color, int year, int price, long rentalId, MultipartFile photo1, MultipartFile photo2, MultipartFile photo3) {
        Optional<CarEntity> carEntity = carRepository.findById(id);
        if (carEntity.isEmpty()) {
            throw new ServiceException(500, "Car not found");
        }
        try {
            CarEntity car = carEntity.get();
            car.setMark(mark);
            car.setModel(model);
            car.setColor(color);
            car.setYear(year);
            car.setPrice(price);
            car.setPhoto1(photo1.getBytes());
            car.setPhoto2(photo2 != null ? photo2.getBytes() : null);
            car.setPhoto3(photo3 != null ? photo3.getBytes() : null);
            carRepository.save(car);
        } catch (IOException e) {
            throw new ServiceException(500, "Error when convert photo to byte array");
        }
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public List<User> getModeratorsForRental() {
        return userRepository.getModeratorsForRental().stream().map(User::new).toList();
    }

    public Rental getRental(Long id) {
        return rentalRepository.findById(id).map(Rental::new).orElse(null);
    }

    public Car getCar(Long id) {
        return carRepository.findById(id).map(Car::new).orElse(null);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll().stream().map(Car::new).toList();
    }
}
