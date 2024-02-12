package com.rohit.controller;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

import com.rohit.services.Services;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    static final Logger logger = LogManager.getLogger(LambdaHandler.class);


    //@Validation(inboundSchema = "classpath:/schema.json")
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent request, Context context)
    {
        Services service = new Services();

            //ValidationUtils.validate(request.getBody(),"classpath:/schema.json");
            if(service.checkForLogin(request,context))
            {
                String method = request.getHttpMethod();
                    switch (method) {
                        case "POST":
                            return service.validateUser(request,context);
                        default:
                            logger.fatal("UnSupportedOperationException triggered for Login :: " +method);
                            return service.errorResponse("UnSupportedOperationException triggered for Login :: "+method,405);
                    }

            }else{
                String method = request.getHttpMethod();
                    switch (method) {
                        case "POST":
                            if (service.validateInput(request, context)) {
                                return service.saveEmployee(request, context);
                            } else {
                                logger.fatal("InvalidPropertiesFormatException triggered ");
                                return service.errorResponse("InvalidPropertiesFormatException triggered for Register ",406);
                            }

                        case "GET":
                            if (request.getPathParameters() != null) {
                                return service.getEmployeeById(request, context);
                            }
                            return service.getEmployees(request, context);

                        case "DELETE":
                            return service.deleteById(request, context);
                        default:
                            logger.fatal("UnSupportedOperationException triggered ::"+method);
                            return service.errorResponse("UnSupportedOperationException triggered :: "+method,405);
                    }
            }
        }
    }

