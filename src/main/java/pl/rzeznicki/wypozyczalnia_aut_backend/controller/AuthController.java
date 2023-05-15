package pl.rzeznicki.wypozyczalnia_aut_backend.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.AuthenticateUser;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.LoginRequest;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.SignupRequest;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.UserRepository;
import pl.rzeznicki.wypozyczalnia_aut_backend.service.AuthService;

import java.util.Arrays;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthenticateUser authenticateUser = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authenticateUser.getJwt()).body(authenticateUser.getUserInfoResponse());
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest, "ROLE_USER");
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/signupModerator")
    public ResponseEntity<?> registerModerator(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest, "ROLE_MODERATOR");
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/signupAdmin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest, "ROLE_ADMIN");
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, authService.logout()).body("");
    }
}
