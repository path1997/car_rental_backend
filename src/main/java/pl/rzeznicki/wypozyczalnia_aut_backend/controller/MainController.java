package pl.rzeznicki.wypozyczalnia_aut_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.CarEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateCar;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.CreateRental;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.*;
import pl.rzeznicki.wypozyczalnia_aut_backend.service.MainService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/main")
@AllArgsConstructor
public class MainController {
    private MainService mainService;

    @GetMapping("/home")
    public ResponseEntity<List<Car>> getHomePage(){
        return ResponseEntity.ok(mainService.getHomePage());
    }

    @GetMapping("/user/getModerators")
    public ResponseEntity<List<User>> getModeratorsForRental(){
        return ResponseEntity.ok(mainService.getModeratorsForRental());
    }

    @GetMapping("/user/history")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Order>> getUserOrders(){
        return ResponseEntity.ok(mainService.getUserOrders());
    }

    @PostMapping("/user/rentcar/{carId}")
    @PreAuthorize("hasRole('USER')")
    public void rentCar(@PathVariable("carId") Long carId){
        mainService.rentCar(carId);
    }

    @PostMapping("/user/returncar/{orderId}/{rentalId}")
    @PreAuthorize("hasRole('USER')")
    public void returnCar(@PathVariable("orderId") Long orderId, @PathVariable("rentalId") Long rentalId){
        mainService.returnCar(orderId, rentalId);
    }

    @GetMapping("/rental/getRentals")
    public ResponseEntity<List<Rental>> getRentals(){
        return ResponseEntity.ok(mainService.getRentals());
    }

    @GetMapping("/rental/get/{id}")
    public ResponseEntity<Rental> getRental(@PathVariable("id") Long id){
        return ResponseEntity.ok(mainService.getRental(id));
    }

    @PostMapping("/rental/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public void createRental(
            @RequestParam(value = "city") String city,
            @RequestParam(value = "address") String address,
            @RequestParam(value = "phone") String phone,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "photo") MultipartFile photo
    ){
        mainService.createRental(city, address, phone, email, photo);
    }

    @DeleteMapping("/rental/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public void deleteRental(@PathVariable("id") Long id){
        mainService.deleteRental(id);
    }

    @PostMapping("/rental/modify/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public void modifyRental(@PathVariable("id") Long id, @RequestBody CreateRental createRental){
        mainService.modifyRental(id, createRental);
    }

    @PostMapping("/car/create")
    @PreAuthorize("hasRole('MODERATOR')")
    public void createCar(@RequestParam(value = "mark") String mark,
                          @RequestParam(value = "model") String model,
                          @RequestParam(value = "color") String color,
                          @RequestParam(value = "year") int year,
                          @RequestParam(value = "price") int price,
                          @RequestParam(value = "rentalId") long rentalId,
                          @RequestParam(value = "photo1") MultipartFile photo1,
                          @RequestParam(required = false, value = "photo2") MultipartFile photo2,
                          @RequestParam(required = false, value = "photo3") MultipartFile photo3){
        mainService.createCar(mark, model, color, year, price, rentalId, photo1, photo2, photo3);
    }

    @PostMapping("/car/modify/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public void modifyCar(@PathVariable("id") Long id,
                          @RequestParam(value = "mark") String mark,
                          @RequestParam(value = "model") String model,
                          @RequestParam(value = "color") String color,
                          @RequestParam(value = "year") int year,
                          @RequestParam(value = "price") int price,
                          @RequestParam(value = "rentalId") long rentalId,
                          @RequestParam(value = "photo1") MultipartFile photo1,
                          @RequestParam(required = false, value = "photo2") MultipartFile photo2,
                          @RequestParam(required = false, value = "photo3") MultipartFile photo3){
        mainService.modifyCar(id,mark, model, color, year, price, rentalId, photo1, photo2, photo3);
    }

    @GetMapping("/car/get/{id}")
    public ResponseEntity<Car> getCar(@PathVariable("id") Long id){
        return ResponseEntity.ok(mainService.getCar(id));
    }

    @DeleteMapping("/car/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public void deleteCar(@PathVariable("id") Long id){
        mainService.deleteCar(id);
    }

    @GetMapping("/car/get/all")
    public ResponseEntity<List<Car>> getAllCars(){
        return ResponseEntity.ok((mainService.getAllCars()));
    }

}
