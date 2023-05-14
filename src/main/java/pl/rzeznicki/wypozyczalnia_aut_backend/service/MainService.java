package pl.rzeznicki.wypozyczalnia_aut_backend.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.*;

@Service
@AllArgsConstructor
public class MainService {
    private CarRepository carRepository;
    private OrderRepository orderRepository;
    private RentalRepository rentalRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

}
