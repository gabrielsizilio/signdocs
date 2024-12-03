package io.github.gabrielsizilio.signdocs.repository;

import io.github.gabrielsizilio.signdocs.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    UserDetails findUserByEmail(String email);
}
