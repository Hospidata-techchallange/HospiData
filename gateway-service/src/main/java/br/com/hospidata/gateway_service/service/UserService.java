package br.com.hospidata.gateway_service.service;

import br.com.hospidata.gateway_service.controller.dto.ChangePasswordRequest;
import br.com.hospidata.gateway_service.controller.dto.UserResponse;
import br.com.hospidata.gateway_service.entity.User;
import br.com.hospidata.gateway_service.repository.UserRepository;
import br.com.hospidata.gateway_service.service.exceptions.DuplicateKeyException;
import br.com.hospidata.gateway_service.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.repository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(User user) {
        if (repository.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateKeyException("User", "email", user.getEmail());
        }
        var now = LocalDateTime.now();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(now);
        user.setLastUpdatedAt(now);
        user.setActive(true);
        return repository.save(user);
    }

    public User findUserById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
    }

    public List<User> findAllUsers(Boolean active) {
        if (active == null) {
            return repository.findAll();
        }
        return repository.findByActive(active);
    }

    public User updateUser(UUID id, User userToUpdate) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));

        repository.findByEmail(userToUpdate.getEmail()).ifPresent(existingUser -> {
            if (!existingUser.getId().equals(id)) {
                throw new DuplicateKeyException("User", "email", userToUpdate.getEmail());
            }
        });

        user.setName(userToUpdate.getName());
        user.setEmail(userToUpdate.getEmail());
        user.setRole(userToUpdate.getRole());
        user.setLastUpdatedAt(LocalDateTime.now());
        return repository.save(user);
    }

    public void deleteUser(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        repository.delete(user);
    }

    public void enableByUserId(UUID id) {
        User user = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        user.setActive(true);
        user.setLastUpdatedAt(LocalDateTime.now());
        repository.save(user);
    }

    public User findUserByEmail(String email) {
        return repository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }
}