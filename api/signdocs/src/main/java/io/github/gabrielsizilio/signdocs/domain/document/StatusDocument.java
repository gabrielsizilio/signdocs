package io.github.gabrielsizilio.signdocs.domain.document;

import lombok.Getter;

@Getter
public enum StatusDocument {
    SIGNED("signed"),
    UNSIGNED("unsigned"),;

    private final String statusDocument;

    private StatusDocument(String statusDocument) {
        this.statusDocument = statusDocument;
    }

}
