package org.example.mobile_banking.feature.fileUpload;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${spring.file-server.server-path}")
    private String serverPath;
    @Value("${spring.file-server.base-url}")
    private String baseUrl;

    @Override
    public FileUploadResponse uploadFile(MultipartFile file) {

        String extension = file.getContentType().split("/")[1];
        String newFileName = String.format("%s.%s",UUID.randomUUID(),extension);
        log.info("Extension: {}", extension);
        log.info("New File: {}", newFileName);
        Path path = Paths.get(serverPath + newFileName);
        try {
            Files.copy(file.getInputStream(),path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File Upload failed");
        }

        return FileUploadResponse.builder().name(newFileName).uri(baseUrl+newFileName).contentType(file.getContentType()).size(file.getSize()).build();
    }

    @Override
    public List<FileUploadResponse> uploadMultipleFiles(List<MultipartFile> files) {
        List<FileUploadResponse>fileUploadResponses = new ArrayList<>();
        files.forEach(file -> {
            FileUploadResponse fileUploadResponse = uploadFile(file);
            fileUploadResponses.add(fileUploadResponse);
        });
        return fileUploadResponses;
    }

    @Override
    public void deleteFileByName(String fileName) {
        Path path = Paths.get(serverPath + fileName);
        if (Files.exists(path)) {
            try {
                Files.delete(path);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"File is failed to delete");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"File Not Found");
        }
    }
}
