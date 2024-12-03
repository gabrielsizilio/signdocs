package io.github.gabrielsizilio.signdocs.services;

import io.github.gabrielsizilio.signdocs.domain.authentication.RegisterRequestDTO;
import io.github.gabrielsizilio.signdocs.domain.user.User;
import io.github.gabrielsizilio.signdocs.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;
    private final UserRepository userRepository;

    public UserService(UserRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    //CREATE
    public Optional<User> createUser(RegisterRequestDTO data) {
        Optional<User> user = repository.findByEmail(data.email());
        if(user.isPresent()) {
            return Optional.empty();
        }

        User newUser = new User(data.firstName(), data.lastName(), data.email(), data.password(), data.role());

        repository.save(newUser);
        return Optional.of(newUser);
    }

    //READ
    public UserDetails findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    //UPDATE

    //DELETE
}
