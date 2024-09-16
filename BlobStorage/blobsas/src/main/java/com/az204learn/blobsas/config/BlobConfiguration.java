package com.az204learn.blobsas.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BlobConfiguration {

    @Bean("testBlobContainerClient")
    public BlobContainerClient testBlobContainerClient111(BlobServiceClient blobServiceClient) {
        return blobServiceClient.getBlobContainerClient("testcontainer");
    }
}
