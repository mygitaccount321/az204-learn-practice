package org.example.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class BlobTriggerJava {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path. The blob contents are provided as input to this function.
     */
    @FunctionName("BlobTriggerJava")
//    @StorageAccount(value = "az204learnstorage")
    public void run(
        @BlobTrigger(name = "content", path = "mycontainer1/{name}.{blobextension}", dataType = "binary") byte[] content,
        @BindingName("name") String name,
        @BindingName("blobextension") String blobextension,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Blob trigger function processed a blob. Name: " + name +"."+blobextension+ "\n  Size: " + content.length + " Bytes");
    }
}