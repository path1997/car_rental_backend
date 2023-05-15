package pl.rzeznicki.wypozyczalnia_aut_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rzeznicki.wypozyczalnia_aut_backend.exception.ServiceException;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.AuthenticateUser;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.RoleEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.db.UserEntity;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.LoginRequest;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody.SignupRequest;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.UserInfoResponse;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.RoleRepository;
import pl.rzeznicki.wypozyczalnia_aut_backend.repository.UserRepository;
import pl.rzeznicki.wypozyczalnia_aut_backend.security.jwt.JwtUtils;
import pl.rzeznicki.wypozyczalnia_aut_backend.security.model.UserDetailsImpl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    public AuthenticateUser authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new AuthenticateUser(jwtCookie.toString(), new UserInfoResponse(userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }

    public void registerUser(SignupRequest signUpRequest, String role) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new ServiceException(500, "User already exist");
        }
        UserEntity user = new UserEntity(signUpRequest.getFirstName(), signUpRequest.getSecondName(), signUpRequest.getPhone(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        RoleEntity userRole = roleRepository.findByName(role)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setRoleEntity(userRole);
        userRepository.save(user);
    }

    public String logout(){
        return jwtUtils.getCleanJwtCookie().toString();
    }
}
