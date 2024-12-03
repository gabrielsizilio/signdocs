package io.github.gabrielsizilio.signdocs.controllers;

import io.github.gabrielsizilio.signdocs.domain.authentication.AuthenticationRequestDTO;
import io.github.gabrielsizilio.signdocs.domain.authentication.LoginResponseDTO;
import io.github.gabrielsizilio.signdocs.domain.authentication.RegisterRequestDTO;
import io.github.gabrielsizilio.signdocs.domain.user.User;
import io.github.gabrielsizilio.signdocs.infra.security.TokenService;
import io.github.gabrielsizilio.signdocs.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserService userService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Validated AuthenticationRequestDTO data) {
        var UserPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

        var auth = this.authenticationManager.authenticate(UserPassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated RegisterRequestDTO data) {

        return userService.createUser(data)
                .map(user -> ResponseEntity.status(HttpStatus.CREATED).body("User created succefully"))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User could not be created"));
    }
}
