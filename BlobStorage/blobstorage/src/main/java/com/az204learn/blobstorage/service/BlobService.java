package com.az204learn.blobstorage.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.models.AccessTier;
import com.azure.storage.blob.models.BlobHttpHeaders;
import com.azure.storage.blob.models.BlobProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

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
        BlobClient blobClient = blobContainerClient.getBlobClient(file.getOriginalFilename());
        blobClient.upload(file.getInputStream(), file.getSize(), true);
        AccessTier accessTier = AccessTier.COOL;
        blobClient.setAccessTier(accessTier);
        blobClient.delete();
    }

    public void setProperties(String blobName)  {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);
        //Set and retrieve metadata
        Map<String, String> metadata = new HashMap<String, String>();
        metadata.put("docType", "text");
        metadata.put("category", "reference1");
        metadata.put("category1", "reference2");

        try {
            blobClient.setMetadata(metadata);
            System.out.printf("Set metadata completed %n");
        } catch (UnsupportedOperationException error) {
            System.out.printf("Failure while setting metadata %n");
        }

        //getProperties
        BlobProperties properties = blobClient.getProperties();

        System.out.printf("BlobType: %s%n", properties.getBlobType());
        System.out.printf("BlobSize: %d%n", properties.getBlobSize());
        System.out.printf("ContentLanguage: %s%n", properties.getContentLanguage());
        System.out.printf("ContentType: %s%n", properties.getContentType());
    }

    public void readBlobMetadata(String blobName)  {
        BlobClient blobClient = blobContainerClient.getBlobClient(blobName);

        //retrieve metadata
        BlobProperties properties = blobClient.getProperties();
        System.out.printf("Blob metadata: %n");

        properties.getMetadata().entrySet().forEach(metadataItem -> {
            System.out.printf(" %s = %s%n", metadataItem.getKey(), metadataItem.getValue());
        });
        //Set Properties
        // Set the ContentLanguage and ContentType headers, and populate the remaining
        // headers from the existing properties
        BlobHttpHeaders blobHeaders = new BlobHttpHeaders()
                .setContentLanguage("en-us")
                .setContentType("text/plain")
                .setCacheControl(properties.getCacheControl())
                .setContentDisposition(properties.getContentDisposition())
                .setContentEncoding(properties.getContentEncoding())
                .setContentMd5(properties.getContentMd5());

        blobClient.setHttpHeaders(blobHeaders);
        System.out.println("Set HTTP headers completed");
    }
}
