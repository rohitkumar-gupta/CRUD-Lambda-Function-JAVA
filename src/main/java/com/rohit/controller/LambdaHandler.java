package com.rohit.controller;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.rohit.exceptions.userExceptions;
import com.rohit.services.Services;
//import  software.amazon.lambda.powertools.validation.Validation;
//import software.amazon.lambda.powertools.validation.ValidationException;


public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    //@Validation(inboundSchema = "classpath:/schema.json")
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context)
    {
        Services service = new Services();

            //ValidationUtils.validate(request.getBody(),"classpath:/schema.json");
            String method = request.getHttpMethod();
            switch (method) {
                case "POST":
                    return service.saveEmployee(request, context);
                case "GET":
                    if (request.getPathParameters() != null) {
                        return service.getEmployeeById(request, context);
                    }
                    return service.getEmployees(request, context);

                case "DELETE":
                    return service.deleteById(request, context);
                default:
                    throw new Error("Unsupported Methods:::" + request.getHttpMethod());
            }
    }



}
