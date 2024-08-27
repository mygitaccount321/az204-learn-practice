package org.example.functions;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerJava {
    /**
     * This function listens at endpoint "/api/HttpTriggerJava". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpTriggerJava
     * 2. curl {your host}/api/HttpTriggerJava?name=HTTP%20Query
     * Refe doc: https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-http-webhook?tabs=isolated-process%2Cfunctionsv2&pivots=programming-language-java
     */
    @FunctionName("HttpTriggerJava")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET, HttpMethod.POST}, webHookType="genericJson", authLevel = AuthorizationLevel.FUNCTION) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String query = request.getQueryParameters().get("name");
        String name = request.getBody().orElse(query);

        if (name == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + name).build();
        }
    }

    //Refe doc: https://learn.microsoft.com/en-us/azure/azure-functions/functions-bindings-http-webhook?tabs=isolated-process%2Cfunctionsv2&pivots=programming-language-java
    @FunctionName("TriggerStringGet")
    public HttpResponseMessage run1(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) {
        context.getLogger().info("Get Parameters are "+ request.getQueryParameters());

        String id = request.getQueryParameters().getOrDefault("id","");


        if(id.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Document Not Found")
                    .build();
        } else {
            final String name = "test_name";
            final String jsonDocument = "{\"id\":\"" + id + "\", " +
                    "\"description\": \"" + name + "\"}";
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonDocument)
                    .build();
        }
    }


    @FunctionName("TriggerStringPost")
    public HttpResponseMessage run2(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST},
                    route = "post-triogger",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context
    ) {
            context.getLogger().info("Request body is: "+request.getBody().orElse(""));

            if(!request.getBody().isPresent()) {
                return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                        .body("Document not found")
                        .build();
            } else {
                final String body = request.getBody().get();
                final String jsonDocument = "{\"id\":\"123456\", " +
                        "\"description\": \"" + body + "\"}";
                return request.createResponseBuilder(HttpStatus.OK)
                        .header("Content-Type", "application/json")
                        .body(jsonDocument)
                        .build();
            }
    }


    @FunctionName("TriggerStringRoute")
    public HttpResponseMessage run3(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET},
                    route = "trigger/{id}/{name=EMPTY}",
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            @BindingName("id") String id,
            @BindingName("name") String name,
            final ExecutionContext context
    ) {
        context.getLogger().info("Route parameters are: " + id);

        if(id == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Document Not found")
                    .build();
        } else {
            final String jsonDocument = "{\"id\":\"" + id + "\", " +
                    "\"description\": \"" + name + "\"}";
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(jsonDocument)
                    .build();
        }
    }

    @FunctionName("TriggerPojoPost")
    public HttpResponseMessage run4(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<ToDoItem>> request,
            final ExecutionContext context
    ) {
        context.getLogger().info("Request body is: " + request.getBody().orElse(null));
        if (!request.getBody().isPresent()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Document not found.")
                    .build();
        } else {
            final ToDoItem body = request.getBody().get();
            return request.createResponseBuilder(HttpStatus.OK)
                    .header("Content-Type", "application/json")
                    .body(body)
                    .build();
        }
    }

    //https://learn.microsoft.com/en-us/aspnet/web-api/overview/web-api-routing-and-actions/attribute-routing-in-web-api-2#constraints
    @FunctionName("Routeconstaints")
    public HttpResponseMessage HttpTrigger(
            @HttpTrigger(name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.FUNCTION,
                    route = "products/{category:alpha}/{id:int}") HttpRequestMessage<String> request,
            @BindingName("category") String category,
            @BindingName("id") int id,
            final ExecutionContext context) {

        String message = String.format("Category  %s, ID: %d", category, id);
        return request.createResponseBuilder(HttpStatus.OK).body(message).build();
    }
}
