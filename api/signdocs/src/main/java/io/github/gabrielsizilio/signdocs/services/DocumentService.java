package io.github.gabrielsizilio.signdocs.services;

import io.github.gabrielsizilio.signdocs.domain.document.Document;
import io.github.gabrielsizilio.signdocs.domain.document.DocumentDTO;
import io.github.gabrielsizilio.signdocs.domain.document.DocumentResponseDTO;
import io.github.gabrielsizilio.signdocs.domain.document.StatusDocument;
import io.github.gabrielsizilio.signdocs.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DocumentService {

    private final DocumentRepository repository;

    @Value("${upload.document.directory}")
    private String uploadDirectory;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    private void ensureDirectoryExists() {
        File file = new File(uploadDirectory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }
    @Transactional
    public List<DocumentResponseDTO> findAllDocuments () {
        List<Document> documents = repository.findAll();

        return documents.stream().map(
                document -> new DocumentResponseDTO(
                        document.getId(),
                        document.getName(),
                        document.getDescription(),
                        document.getStatus().name(),
                        document.getCreatedAt(),
                        document.getUpdatedAt()))
                .collect(Collectors.toList());
    }

    @Transactional
    public Optional<DocumentResponseDTO> findDocumentById (UUID id) {
        Optional<Document> document = repository.findById(id);
        return document.map(doc -> new DocumentResponseDTO(
                doc.getId(),
                doc.getName(),
                doc.getDescription(),
                doc.getStatus().name(),
                doc.getCreatedAt(),
                doc.getUpdatedAt()));
    }

    @Transactional
    public Document uploadDocument(DocumentDTO data) throws IOException {

        ensureDirectoryExists();
        if(data == null || data.document() == null || data.document().isEmpty()) {
            throw new IllegalArgumentException("Invalid document: file missing or empty");
        }
        MultipartFile file = data.document();

        Document document = new Document();
        document.setId(generateUUIDRandom());

        String fileName = data.name();
        if(fileName == null || fileName.isEmpty()) {
            fileName = file.getOriginalFilename();
        }

        String documentName = generateFileName(fileName, document.getId());
        Path path = Paths.get(uploadDirectory, documentName);
        Files.copy(file.getInputStream(), path);

        document.setName(fileName);
        document.setStatus(StatusDocument.UNSIGNED);
        document.setDescription(data.description());
        document.setUrl(path.toString());

        return repository.save(document);
    }

    @Transactional
    public Boolean deleteDocumentById (UUID id) throws IOException {

        Optional<Document> document = repository.findById(id);
        if(document.isPresent()) {
            Document doc = document.get();

            String filePath = doc.getUrl();
            if(filePath != null) {
                Files.delete(Paths.get(filePath));
           }

            repository.delete(document.get());
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    private String generateFileName(String originalFilename, UUID id) {
        return id + "_" + originalFilename;
    }

    private UUID generateUUIDRandom() {
        return UUID.randomUUID();
    }
}
