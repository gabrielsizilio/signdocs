package io.github.gabrielsizilio.signdocs.domain.document;

import java.time.LocalDateTime;
import java.util.UUID;

public record DocumentResponseDTO(UUID id, String name, String description, String status, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
