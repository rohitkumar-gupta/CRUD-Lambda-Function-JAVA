package com.rohit.utility;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohit.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utility {

    public APIGatewayProxyResponseEvent createAPIResponse(String body, int statusCode, Map<String,String> headers)
    {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setBody(body);
        response.setStatusCode(statusCode);
        response.setHeaders(headers);
        return response;
    }
    public Map<String,String> createHeaders(){
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type","application/json");
        headers.put("X-amazon-author","Rohit");
        headers.put("X-amazon-apiVersion","v1");
        return  headers ;
    }

    public String convertObjToString(Employee employee, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(employee);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }
    public  Employee convertStringToObj(String jsonBody,Context context){
        Employee employee = null;
        try {
            employee =   new ObjectMapper().readValue(jsonBody,Employee.class);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting string to obj:::" + e.getMessage());
        }
        return employee;
    }
    public String convertListToString(List<Employee> employees, Context context){
        String jsonBody = null;
        try {
            jsonBody =   new ObjectMapper().writeValueAsString(employees);
        } catch (JsonProcessingException e) {
            context.getLogger().log( "Error while converting obj to string:::" + e.getMessage());
        }
        return jsonBody;
    }


}
