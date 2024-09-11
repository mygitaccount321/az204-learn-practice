package com.az204learn.blobstorage.contoller;

import com.az204learn.blobstorage.service.BlobServiceCloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/cloud")
public class BlobControllerCloud {

    @Autowired
    private BlobServiceCloud blobServiceCloud;

    @GetMapping("/content")
    public String downloadFile(String blobname) throws IOException {
        return blobServiceCloud.downloadFile(blobname);
    }

    @GetMapping("/content1")
    public String downloadFile1(String blobname) throws IOException {
        return blobServiceCloud.downloadFile1(blobname);
    }

    @PostMapping("/file")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        // Process the file (save it, validate it, etc.)
        String fileName = file.getOriginalFilename();
        blobServiceCloud.writeBlobFile(file);
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }

    @PostMapping("/file1")
    public ResponseEntity<String> handleFileUpload1(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        // Process the file (save it, validate it, etc.)
        String fileName = file.getOriginalFilename();
        blobServiceCloud.writeBlobFile1(file);
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }
}
