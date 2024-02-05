package com.rohit.controller;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.rohit.services.Services;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context)
    {
        Services service = new Services();
        String method = request.getHttpMethod();
        switch (method) {
            case "POST":
                //check whether the employee ID already exists

                return service.saveEmployee(request,context);

            case "GET":
                if (request.getPathParameters() != null) {
                    return service.getEmployeeById(request,context);
                }
                return service.getEmployees(request,context);

            case "DELETE":
                if (request.getPathParameters() != null) {
                    return service.deleteById(request,context);
                }
                return service.deleteById(request,context);
            default:
                throw new Error("Unsupported Methods:::" + request.getHttpMethod());

        }
    }



}
