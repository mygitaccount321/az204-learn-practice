package org.example.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

import java.util.ArrayList;

/**
 * Azure Functions with Cosmos DB trigger.
 */
public class CosmosDBTriggerJava {
    /**
     * This function will be invoked when there are inserts or updates in the specified database and collection.
     */
    @FunctionName("CosmosDBTriggerJava")
    public void run(
        @CosmosDBTrigger(
            name = "items",
            databaseName = "college",
            containerName = "student",
            leaseContainerName="leaseContainerFx",
            connection = "COSMOSDB_CONNECTION_DEV",
            createLeaseContainerIfNotExists = true
        )
        Object inputItem,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Cosmos DB trigger function executed.");
        ArrayList<?> inputItems = (ArrayList<?>) inputItem;
        context.getLogger().info("Documents modified: " + inputItems.size());
    }
}
