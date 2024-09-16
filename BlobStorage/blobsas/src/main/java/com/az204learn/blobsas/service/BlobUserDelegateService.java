package com.az204learn.blobsas.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.UserDelegationKey;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.common.sas.AccountSasPermission;
import com.azure.storage.common.sas.AccountSasResourceType;
import com.azure.storage.common.sas.AccountSasService;
import com.azure.storage.common.sas.AccountSasSignatureValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.OffsetDateTime;

@Service
public class BlobUserDelegateService {

    @Autowired
    @Qualifier("testBlobContainerClient")
    private BlobContainerClient blobContainerClient;


    @Autowired
    private BlobServiceClient blobServiceClient;

    public Boolean isBlobExist(String blobName) {
        return blobContainerClient.getBlobClient(blobName).exists();
    }
//https://learn.microsoft.com/en-us/azure/storage/blobs/storage-blob-user-delegation-sas-create-java?tabs=blob
    public String downloadFileSas(String blobName) throws IOException {

        UserDelegationKey userDelegationKey = blobServiceClient.getUserDelegationKey(
                OffsetDateTime.now().minusMinutes(5),
                OffsetDateTime.now().plusDays(1));

        OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(1);

        // Assign read permissions to the SAS token
        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true);

        BlobServiceSasSignatureValues sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setStartTime(OffsetDateTime.now().minusMinutes(5));
//                .setIdentifier("policyid");

        return blobContainerClient.getBlobClient(blobName).generateUserDelegationSas(sasSignatureValues, userDelegationKey);
    }
//https://learn.microsoft.com/en-us/azure/storage/blobs/sas-service-create-java?tabs=blob
    public String downloadFileServiceSas(String blobName) throws IOException {

        OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(1);

        // Assign read permissions to the SAS token
        BlobSasPermission sasPermission = new BlobSasPermission()
                .setReadPermission(true);

        BlobServiceSasSignatureValues sasSignatureValues = new BlobServiceSasSignatureValues(expiryTime, sasPermission)
                .setStartTime(OffsetDateTime.now().minusMinutes(5));
        return blobContainerClient.getBlobClient(blobName).generateSas(sasSignatureValues);
    }

    public String createAccountSAS() throws IOException {

        OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(1);
        AccountSasPermission accountSasPermission = new AccountSasPermission()
                .setReadPermission(true);
        AccountSasService services = new AccountSasService()
                .setBlobAccess(true);
        AccountSasResourceType resourceTypes = new AccountSasResourceType()
                .setService(true);

        // Generate the account SAS
        AccountSasSignatureValues accountSasValues = new AccountSasSignatureValues(
                expiryTime,
                accountSasPermission,
                services,
                resourceTypes);

        String accountName = "<account-name>";
        String accountKey = "<account-key>";
        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint(String.format("https://%s.blob.core.windows.net/", accountName))
                .credential(credential)
                .buildClient();
        String sasToken = blobServiceClient.generateAccountSas(accountSasValues);
        BlobServiceClient sasServiceClient = new BlobServiceClientBuilder()
                .endpoint(blobServiceClient.getAccountUrl())
                .sasToken(sasToken)
                .buildClient();
        return null;
    }
}
