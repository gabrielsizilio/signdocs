package io.github.gabrielsizilio.signdocs.domain.document;

import io.github.gabrielsizilio.signdocs.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "document_signatures")
@Table(name = "document_signatures")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DocumentSignatures {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "signer_id", nullable = false)
    private User signer;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    private LocalDateTime signedAt;

    @PrePersist
    protected void onCreate() {
        this.signedAt = LocalDateTime.now();
    }
}
