package io.github.gabrielsizilio.signdocs.repository;

import io.github.gabrielsizilio.signdocs.domain.document.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document, UUID> {
}
