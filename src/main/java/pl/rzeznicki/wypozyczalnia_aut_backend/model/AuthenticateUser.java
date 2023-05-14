package pl.rzeznicki.wypozyczalnia_aut_backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pl.rzeznicki.wypozyczalnia_aut_backend.model.responseBody.UserInfoResponse;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticateUser {
    private String jwt;
    private UserInfoResponse userInfoResponse;
}
