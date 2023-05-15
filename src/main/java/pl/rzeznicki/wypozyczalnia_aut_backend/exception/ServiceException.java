package pl.rzeznicki.wypozyczalnia_aut_backend.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class ServiceException extends RuntimeException {
    private int status;
    private String message;

    public ServiceException(int status, String message) {
        super();
        this.status = status;
        this.message = message;
    }
}
