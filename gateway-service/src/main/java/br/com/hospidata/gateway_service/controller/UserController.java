package br.com.hospidata.gateway_service.controller;

import br.com.hospidata.gateway_service.controller.docs.UserControllerDoc;
import br.com.hospidata.gateway_service.controller.dto.*;
import br.com.hospidata.gateway_service.entity.enums.Role;
import br.com.hospidata.gateway_service.mapper.UserMappper;
import br.com.hospidata.gateway_service.security.aspect.CheckRole;
import br.com.hospidata.gateway_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController implements UserControllerDoc {

    private final UserService service;
    private final UserMappper mapper;

    public UserController(UserService service , UserMappper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        var result = service.createUser(mapper.toEntity(userRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }

    @Override
    @GetMapping
    @CheckRole(Role.ADMIN)
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(required = false) Boolean active) {
        return ResponseEntity.ok(mapper.toListResponse(service.findAllUsers(active)));
    }

    @Override
    @GetMapping("/{id}")
    @CheckRole(Role.ADMIN)
    public ResponseEntity<UserResponse> findUserById(
             @PathVariable UUID id) {
        var result = service.findUserById(id);
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable UUID id,
            @Valid @RequestBody UserRequestUpdate userRequest
    ) {
        var result = service.updateUser(id, mapper.toEntity(userRequest));
        return ResponseEntity.ok(mapper.toResponse(result));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(
            @PathVariable UUID id
    ){
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    @PatchMapping("/enable/{id}")
    public ResponseEntity<Void> enableUserById(
             @PathVariable UUID id) {
        service.enableByUserId(id);
        return ResponseEntity.noContent().build();
    }

}