package pl.rzeznicki.wypozyczalnia_aut_backend.model.requestBody;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String secondName;
 
    @NotBlank
    private String email;

    @NotBlank
    private String phone;
    
    @NotBlank
    private String password;
}
