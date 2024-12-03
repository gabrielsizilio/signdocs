package io.github.gabrielsizilio.signdocs.controllers;

import io.github.gabrielsizilio.signdocs.domain.document.Document;
import io.github.gabrielsizilio.signdocs.domain.document.DocumentDTO;
import io.github.gabrielsizilio.signdocs.domain.document.DocumentResponseDTO;
import io.github.gabrielsizilio.signdocs.services.DocumentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/document")
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<DocumentResponseDTO>> findAllDocuments() {
        List<DocumentResponseDTO> documents = documentService.findAllDocuments();
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/me")
    public ResponseEntity<DocumentResponseDTO> findDocumentById(@RequestParam UUID id) {
        Optional<DocumentResponseDTO> documentDTO = documentService.findDocumentById(id);
        return documentDTO.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadDocument(@RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "fileName") String fileName,
                                         @RequestParam(value = "fileDescription") String fileDescription) {
        try {
            DocumentDTO documentDTO = new DocumentDTO(file, fileName, fileDescription);
            Document document = documentService.uploadDocument(documentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body("Documents uploaded successfully");
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteDocumentById(@RequestParam UUID id) throws IOException {
        Boolean isDeleted = documentService.deleteDocumentById(id);
        if (isDeleted){
            return ResponseEntity.status(HttpStatus.OK).body("Document deleted succefully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Document could not be deleted");
        }
    }

}
