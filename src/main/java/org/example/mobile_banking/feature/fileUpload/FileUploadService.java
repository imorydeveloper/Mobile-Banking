package org.example.mobile_banking.feature.fileUpload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
public interface FileUploadService {
    FileUploadResponse uploadFile(MultipartFile file);
    List<FileUploadResponse>uploadMultipleFiles(List<MultipartFile> files);
    void deleteFileByName(String fileName);
}


