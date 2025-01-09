package org.example.mobile_banking.feature.fileUpload;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping
    FileUploadResponse upload(@RequestPart MultipartFile file){
        return fileUploadService.uploadFile(file);
    }
    @PostMapping("/multiple")
    List<FileUploadResponse> uploadMultipleFiles(@RequestPart List<MultipartFile> files){
        return fileUploadService.uploadMultipleFiles(files);
    }
    @DeleteMapping("/{file-name}")
    void deleteFileByName(@PathVariable("file-name") String fileName) {
        fileUploadService.deleteFileByName(fileName);
    }
}
