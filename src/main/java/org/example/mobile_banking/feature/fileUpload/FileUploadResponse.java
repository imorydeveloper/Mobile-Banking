package org.example.mobile_banking.feature.fileUpload;

import lombok.Builder;

@Builder
public record FileUploadResponse(
        String name,
        String uri,
        String contentType,
        Long size

) {
}
