package com.az204learn.blobstorage.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;

@Service
public class BlobService {


    @Autowired
    @Qualifier("testBlobContainerClient")
    private BlobContainerClient blobContainerClient;

//    https://az204learnstorage.blob.core.windows.net/testcontainer/Dockerfile.txt
    @Value("azure-blob://testcontainer/Dockerfile.txt")
    private Resource blobFile;

    public Boolean isBlobExist(String blobName) {
        return blobContainerClient.getBlobClient(blobName).exists();
    }

//    public String downloadFile(String blobName) throws IOException {
//        return StreamUtils.copyToString(
//                blobContainerClient.getBlobClient(blobName).downloadContent().toStream(),
//                Charset.defaultCharset());
//    }

    public String downloadFile(String blobName) throws IOException {
        return StreamUtils.copyToString(
                this.blobFile.getInputStream(),
                Charset.defaultCharset());
    }


    public void writeBlobFile(MultipartFile file) throws IOException {
        blobContainerClient.getBlobClient(file.getOriginalFilename()).upload(file.getInputStream(), file.getSize());
    }
}
