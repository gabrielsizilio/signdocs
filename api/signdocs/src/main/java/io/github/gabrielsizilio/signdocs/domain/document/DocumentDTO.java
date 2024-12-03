package io.github.gabrielsizilio.signdocs.domain.document;

import org.springframework.web.multipart.MultipartFile;

public record DocumentDTO(MultipartFile document, String name, String description) {
}
