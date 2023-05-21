package pl.rzeznicki.wypozyczalnia_aut_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RentalEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateCar;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateRental;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.UserOrder;
import pl.rzeznicki.wypozyczalnia_aut_backend.service.MainService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class MainController {
    private MainService mainService;

    @GetMapping("/home")
    public ResponseEntity<List<CarEntity>> getHomePage(){
        return ResponseEntity.ok(mainService.getHomePage());
    }

    @GetMapping("/user/history")
    public ResponseEntity<List<UserOrder>> getUserOrders(){
        Long id = null;
        return ResponseEntity.ok(mainService.getUserOrders(id));
    }

    @PostMapping("/user/rentcar/{carId}")
    public void rentCar(@PathVariable("carId") Long carId){
        Long userId = null;
        mainService.rentCar(userId, carId);
    }

    @PostMapping("/user/returncar/{orderId}")
    public void returnCar(@PathVariable("orderId") Long orderId){
        mainService.returnCar(orderId);
    }

    @GetMapping("/rental/getRentals")
    public ResponseEntity<List<RentalEntity>> getRentals(){
        return ResponseEntity.ok(mainService.getRentals());
    }

    @PostMapping("/rental/create")
    public void createRental(@RequestBody CreateRental createRental){
        mainService.createRental(createRental);
    }

    @DeleteMapping("/rental/delete/{id}")
    public void deleteRental(@PathVariable("id") Long id){
        mainService.deleteRental(id);
    }

    @PostMapping("/rental/modify/{id}")
    public void modifyRental(@PathVariable("id") Long id, @RequestBody CreateRental createRental){
        mainService.modifyRental(id, createRental);
    }

    @PostMapping("/car/create")
    public void createCar(@RequestBody CreateCar createCar){
        mainService.createCar(createCar);
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "User Content.";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public String moderatorAccess() {
        return "Moderator Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
