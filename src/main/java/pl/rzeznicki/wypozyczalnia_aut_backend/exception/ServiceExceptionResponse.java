package pl.rzeznicki.wypozyczalnia_aut_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ServiceExceptionResponse {
    private int status;
    private String message;
}
