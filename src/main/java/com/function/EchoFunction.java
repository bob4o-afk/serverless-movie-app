package com.function;

import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

import com.microsoft.azure.functions.*;

public class EchoFunction {
    @FunctionName("EchoFunction")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            @BindingName("param") String param,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        if (param == null || param.isEmpty()) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Please pass a parameter in the query string").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK)
                    .body(param)
                    .build();
        }
    }
}
