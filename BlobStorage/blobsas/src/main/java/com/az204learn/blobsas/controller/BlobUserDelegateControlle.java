package com.az204learn.blobsas.controller;

import com.az204learn.blobsas.service.BlobService;
import com.az204learn.blobsas.service.BlobUserDelegateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/userde")
public class BlobUserDelegateControlle {

    @Autowired
    private BlobUserDelegateService blobUserDelegateService;

    @GetMapping("/content")
    public String downloadFile(String blobname) throws IOException {
        return blobUserDelegateService.downloadFileSas(blobname);
    }

    @GetMapping("/content/servicesas")
    public String downloadFileServicesas(String blobname) throws IOException {
        return blobUserDelegateService.downloadFileServiceSas(blobname);
    }

}
