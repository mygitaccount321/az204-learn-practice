package com.az204learn.blobstorage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.WritableResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
@Service
public class BlobServiceCloud {


    //Code Sample
    // https://github.com/Azure-Samples/azure-spring-boot-samples/blob/main/storage/spring-cloud-azure-starter-storage-blob/storage-blob-sample/src/main/java/com/azure/spring/sample/storage/resource/SampleDataInitializer.java
    static final String BLOB_RESOURCE_PATTERN = "azure-blob://%s/%s";
    static final String BLOB_TESTCONTAINER_CONTAINER = "testcontainer";


    @Autowired
    private ResourceLoader resourceLoader;

    @Value("azure-blob://testcontainer/test123.txt")
    private Resource blobFile;

    public String downloadFile(String blobName) throws IOException {
        return StreamUtils.copyToString(
                this.blobFile.getInputStream(),
                Charset.defaultCharset());
    }

    public String downloadFile1(String blobName) throws IOException {
        Resource storageBlobResource = resourceLoader.getResource(String.format(BLOB_RESOURCE_PATTERN, BLOB_TESTCONTAINER_CONTAINER, blobName));
        return StreamUtils.copyToString(
                storageBlobResource.getInputStream(),
                Charset.defaultCharset());
    }

    public void writeBlobFile(MultipartFile file) throws IOException {
        try (OutputStream os = ((WritableResource) this.blobFile).getOutputStream()) {
            os.write(file.getBytes());
        }
    }

    public void writeBlobFile1(MultipartFile file) throws IOException {
        Resource storageBlobResource = resourceLoader.getResource(String.format(BLOB_RESOURCE_PATTERN, BLOB_TESTCONTAINER_CONTAINER, file.getOriginalFilename()));
        try (OutputStream os = ((WritableResource) storageBlobResource).getOutputStream()) {
            os.write(file.getBytes());
        }
    }
}
