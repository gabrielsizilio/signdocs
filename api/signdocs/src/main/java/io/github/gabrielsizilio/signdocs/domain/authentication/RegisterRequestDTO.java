package io.github.gabrielsizilio.signdocs.domain.authentication;

public record RegisterRequestDTO (String firstName, String lastName, String email, String password, String role) {}
