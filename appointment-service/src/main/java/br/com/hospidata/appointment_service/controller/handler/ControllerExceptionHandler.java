package br.com.hospidata.appointment_service.controller.handler;

import br.com.hospidata.appointment_service.controller.dto.ErrorResponse;
import br.com.hospidata.appointment_service.controller.dto.ErrorResponseInternal;
import br.com.hospidata.appointment_service.controller.dto.ValidationError;
import br.com.hospidata.appointment_service.service.exceptions.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseInternal> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        var status = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = new ErrorResponseInternal(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                request.getMethod()
        );

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerResourceNotFoundExeception (
            ResourceNotFoundException e,
            HttpServletRequest request) {

        var status = HttpStatus.NOT_FOUND.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("Resource Not Found : [{} {}] - {} (status: {})", method, path, e.getMessage(), status);

        return ResponseEntity.status(status).body(new ErrorResponse(Instant.now() , status ,e.getMessage() , method , path));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handlerMethodArgumentNotValidException (
            MethodArgumentNotValidException e,
            HttpServletRequest request) {

        var status = HttpStatus.BAD_REQUEST.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        List<String> errors = new ArrayList<String>();
        for (var error : e.getBindingResult().getFieldErrors()) errors.add(error.getField() + " : " + error.getDefaultMessage());

        log.warn("Validation error: [{} {}] - Invalid fields: {}", method, path, errors);

        return ResponseEntity.status(status).body(new ValidationError(errors , status , method , path));

    }

}
