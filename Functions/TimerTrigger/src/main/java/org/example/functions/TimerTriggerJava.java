package org.example.functions;

import java.time.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with Timer trigger.
 */
public class TimerTriggerJava {
    /**
     * This function will be invoked periodically according to the specified schedule.
     */
    @FunctionName("TimerTriggerJava")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "0 * * * * *") String timerInfo,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Timer trigger function executed at: " + LocalDateTime.now());
    }

    public static int count = 1;

    //Retry policies will work only for Cosmos DB, Event Hub, Kafka, Timer
    //https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-error-pages?tabs=fixed-delay%2Cisolated-process%2Cnode-v4%2Cpython-v2&pivots=programming-language-java
    @FunctionName("TimerTriggerRetry")
    @FixedDelayRetry(maxRetryCount = 1, delayInterval = "00:00:01")
    public void HttpExampleRetry1(
            @TimerTrigger(name = "timerInfo", schedule = "0 0/5 * * * *") String timerInfo,
            final ExecutionContext context) throws Exception {
        context.getLogger().info("====1==============================Java HTTP1 trigger processed a request.");

        if (count < 1) {
            count++;
            context.getLogger().info("==================================Java HTTP trigger processed a request-Error");
            throw new Exception("error");
        }
        context.getLogger().info("=====================Timer is triggered: " + timerInfo);
        count = 0;
    }
}
