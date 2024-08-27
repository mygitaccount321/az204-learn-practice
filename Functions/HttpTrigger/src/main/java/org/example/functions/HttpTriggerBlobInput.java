package org.example.functions;

import java.nio.charset.StandardCharsets;
import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerBlobInput {
    /**
     * This function listens at endpoint "/api/HttpTriggerBlobInput". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTriggerBlobInput
     * 2. curl {your host}/api/HttpTriggerBlobInput?name=HTTP%20Query
     * example tried http://localhost:55665/api/HttpTriggerBlobInput?file=ReadMeFunctions.txt
     * ref doc : https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-storage-blob-input?tabs=python-v2%2Cisolated-process%2Cnodejs-v4&pivots=programming-language-java
     */
    @FunctionName("HttpTriggerBlobInput")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BlobInput(
                    name = "file",
                    dataType = "binary",
                    path = "mycontainer/{Query.file}",
                    connection = "AzureWebJobsStorage")
            byte[] content,
            final ExecutionContext context) {
        return request.createResponseBuilder(HttpStatus.OK)
                .body("The size of \"" + request.getQueryParameters().get("file") + "\" is: " + content.length + " bytes")
                .build();
    }
    @FunctionName("HttpTriggerBlobOut")
    public HttpResponseMessage HttpTriggerBlobOut(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BlobInput(
                    name = "file",
                    dataType = "binary",
                    path = "mycontainer/{Query.file}",
                    connection = "AzureWebJobsStorage")
            byte[] content,
            @BlobOutput(
                    name = "target",
                    //https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-expressions-patterns
                    path = "myblob/{rand-guid}-{DateTime}-{Query.file}-CopyViaHttp",
                    connection = "Myblob")
            OutputBinding<String> outputItem,
            final ExecutionContext context) {

        outputItem.setValue(new String(content, StandardCharsets.UTF_8));

        // build HTTP response with size of requested blob
        return request.createResponseBuilder(HttpStatus.OK)
                .body("The size of \"" + request.getQueryParameters().get("file") + "\" is: " + content.length + " bytes")
                .build();
    }


    @FunctionName("HttpTriggerBlobOut1")
    public HttpResponseMessage HttpTriggerBlobOut1(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BlobInput(
                    name = "file",
                    dataType = "binary",
                    path = "mycontainer/{Query.file}",
                    connection = "AzureWebJobsStorage")
            byte[] content,
            @BlobOutput(
                    name = "target",
                    path = "myblob/{Query.file}-CopyViaHttp",
                    connection = "AzureWebJobsStorage")
            OutputBinding<byte[]> outputItem,
            final ExecutionContext context) {

        outputItem.setValue(content);

        // build HTTP response with size of requested blob
        return request.createResponseBuilder(HttpStatus.OK)
                .body("The size of \"" + request.getQueryParameters().get("file") + "\" is: " + content.length + " bytes")
                .build();
    }
}
