package pl.rzeznicki.wypozyczalnia_aut_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.AvailableCarListResponse;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.*;

import java.util.List;

@Service
@AllArgsConstructor
public class MainService {
    private CarRepository carRepository;
    private OrderRepository orderRepository;
    private RentalRepository rentalRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;


    public List<AvailableCarListResponse> getHomePage() {
        return carRepository.findAllByAvailableTrue().stream().map(AvailableCarListResponse::new).toList();
    }
}
