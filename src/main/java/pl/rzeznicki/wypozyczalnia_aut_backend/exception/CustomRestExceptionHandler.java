package pl.rzeznicki.wypozyczalnia_aut_backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ ServiceException.class })
    public ResponseEntity<ServiceExceptionResponse> handle(ServiceException serviceException){
        return new ResponseEntity<>(new ServiceExceptionResponse(serviceException.getStatus(), serviceException.getMessage()), HttpStatus.valueOf(serviceException.getStatus()));
    }
}
