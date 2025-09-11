package br.com.hospidata.gateway_service.controller.handler;

import br.com.hospidata.gateway_service.controller.dto.ErrorResponse;
import br.com.hospidata.gateway_service.controller.dto.ErrorResponseInternal;
import br.com.hospidata.gateway_service.controller.dto.ValidationError;
import br.com.hospidata.gateway_service.service.exceptions.DuplicateKeyException;
import br.com.hospidata.gateway_service.service.exceptions.ResourceNotFoundException;
import br.com.hospidata.gateway_service.service.exceptions.UnauthorizedException;
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
public class ControllerExeceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ControllerExeceptionHandler.class);

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

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateEmailException (
            DuplicateKeyException e,
            HttpServletRequest request
    ) {

        var status = HttpStatus.CONFLICT.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("Duplicate key violation at [{} {}] - Reason: {}", method, path, e.getMessage());

        return ResponseEntity.status(status).body(new ErrorResponse(Instant.now() , status , e.getMessage() , method , path));

    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateEmailException (
            UnauthorizedException e,
            HttpServletRequest request
    ) {

        var status = HttpStatus.UNAUTHORIZED.value();
        var method = request.getMethod();
        var path = request.getRequestURI();

        log.warn("Unathorized Exeception [{} {}] - Reason: {}", method, path, e.getMessage());

        return ResponseEntity.status(status).body(new ErrorResponse(Instant.now() , status , e.getMessage(), method , path));

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
