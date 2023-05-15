package pl.rzeznicki.wypozyczalnia_aut_backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.AvailableCarListResponse;
import pl.rzeznicki.wypozyczalnia_aut_backend.service.MainService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/test")
@AllArgsConstructor
public class MainController {
    private MainService mainService;

    @GetMapping("/home")
    public ResponseEntity<List<AvailableCarListResponse>> getHomePage(){
        return ResponseEntity.ok(mainService.getHomePage());
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
