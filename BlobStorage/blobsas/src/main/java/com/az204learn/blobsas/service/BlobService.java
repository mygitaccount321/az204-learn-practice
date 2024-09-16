package com.az204learn.blobsas.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.AccessTier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class BlobService {

    @Autowired
    @Qualifier("testBlobContainerClient")
    private BlobContainerClient blobContainerClient;

    public Boolean isBlobExist(String blobName) {
        return blobContainerClient.getBlobClient(blobName).exists();
    }

    public String downloadFile(String blobName) throws IOException {
        return StreamUtils.copyToString(
                blobContainerClient.getBlobClient(blobName).downloadContent().toStream(),
                Charset.defaultCharset());
    }

    public void writeBlobFile(MultipartFile file) throws IOException {
        BlobClient blobClient = blobContainerClient.getBlobClient(file.getOriginalFilename());
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        AccessTier accessTier = AccessTier.HOT;
        blobClient.setAccessTier(accessTier);
    }
}
