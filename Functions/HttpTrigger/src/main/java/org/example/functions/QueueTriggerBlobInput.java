package org.example.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Azure Storage Queue trigger.
 */
public class QueueTriggerBlobInput {
    /**
     * This function will be invoked when a new message is received at the specified path. The message contents are provided as input to this function.
     */
    @FunctionName("QueueTriggerBlobInput")
    public void run(
        @QueueTrigger(name = "filename", queueName = "myqueue-items-sample", connection = "AzureWebJobsStorage") String filename,
        @BlobInput(
                name = "file",
                dataType = "binary",
                path = "mycontainer/{queueTrigger}",
                connection = "AzureWebJobsStorage")
        byte[] content,
        final ExecutionContext context
    ) {
        context.getLogger().info("The size of \"" + filename + "\" is: " + content.length + " bytes");
    }
}
