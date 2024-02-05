package com.rohit.services;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.rohit.utility.Utility;
import com.rohit.model.Employee;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Services {
    private DynamoDBMapper mapper;
    private static String jsonBody = null;
    Utility utility = new Utility();
    //Creating an instance of mapper class
    public void initDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        mapper = new DynamoDBMapper(client);
    }


    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent request ,Context context)
    {
        try{
            initDB();
            context.getLogger().log("I am in Try block of saveEmployee method with data ==>"+request.getBody());
            Employee employee = utility.convertStringToObj(request.getBody(), context);
            context.getLogger().log("Data of ==>"+employee.getName());
            mapper.save(employee);
            context.getLogger().log("Data saved of  ==>"+employee.getName());
        }
        catch(Exception e)
        {
            context.getLogger().log("I am in catch block of saveEmployee method with data ==>"+request.getBody()+"/n Error is "+e.getMessage());
        }
        finally{
            return utility.createAPIResponse("Completed save method invoke",200,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent getEmployees(APIGatewayProxyRequestEvent request, Context context)
    {
        try{
            initDB();
            context.getLogger().log("I am in Try block of getEmployees method");
            List<Employee> employees = mapper.scan(Employee.class,new DynamoDBScanExpression());
            jsonBody =  utility.convertListToString(employees,context);
            context.getLogger().log("fetch employee List:::" + jsonBody);
        }
        catch(Exception e)
        {
            context.getLogger().log("I am in catch block of getEmployee method with data ==>"+request.getPathParameters()+"/n Error is "+e.getMessage());
        }
        finally{
            return utility.createAPIResponse(jsonBody,200,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent getEmployeeById(APIGatewayProxyRequestEvent request, Context context)
    {
        try{
            initDB();
            context.getLogger().log("I am in Try block of getEmployeeByID method");
            String empId = request.getPathParameters().get("empId");
            Employee employee =  mapper.load(Employee.class,empId)  ;
            if(employee!=null) {
                jsonBody = utility.convertObjToString(employee, context);
                context.getLogger().log("fetch employee By ID:::" + jsonBody);
            }else{
                jsonBody = "Employee Not Found Exception :" + empId;
            }
        }
        catch(Exception e)
        {
            context.getLogger().log("I am in catch block of getEmployeeByID method with data ==>"+request.getPathParameters()+"/n Error is "+e.getMessage());
        }
        finally{
            return utility.createAPIResponse(jsonBody,200,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent deleteById(APIGatewayProxyRequestEvent request, Context context)
    {
        try{
            initDB();
            String empId = request.getPathParameters().get("empId");
            Employee employee =  mapper.load(Employee.class,empId)  ;
            if(employee!=null) {
                mapper.delete(employee);
                context.getLogger().log("data deleted successfully :::" + empId);
                jsonBody = "Data deleted SuccessFully";
            }else{
                jsonBody = "Employee Not Found Exception :" + empId;
            }
        }
        catch(Exception e)
        {
            context.getLogger().log("I am in catch block of DeleteEmployee method with id ==>"+request.getPathParameters()+"/n Error is "+e.getMessage());
        }
        finally{
            return utility.createAPIResponse(jsonBody,200,utility.createHeaders());
        }
    }


}
