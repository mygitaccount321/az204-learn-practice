package com.az204learn.blobsas.controller;

import com.az204learn.blobsas.service.BlobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class BlobContoller {

    @Autowired
    private BlobService blobService;

    @GetMapping("/content")
    public String downloadFile(String blobname) throws IOException {
        return blobService.downloadFile(blobname);
    }

    @PostMapping("/file")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }
        // Process the file (save it, validate it, etc.)
        String fileName = file.getOriginalFilename();
        blobService.writeBlobFile(file);
        return ResponseEntity.ok("File uploaded successfully: " + fileName);
    }
}
