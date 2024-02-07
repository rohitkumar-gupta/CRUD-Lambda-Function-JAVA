package com.rohit.services;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.rohit.utility.Utility;
import com.rohit.model.Employee;
import  com.rohit.dao.userDao;


import java.util.List;

public class Services {

    private String jsonBody = null;
    private int statusCode ;
    Utility utility = new Utility();
    //Creating an instance of mapper class
    userDao user = new userDao();

    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent request ,Context context) //working fine
    {
        try{
            context.getLogger().log("Executing saveEmployee method with data ==>"+request.getBody());
            Employee employee = utility.convertStringToObj(request.getBody(), context);
            //Check if ID already exists --> return true
            if(!user.CheckIfExist(employee.getEmpId()))
            {
                context.getLogger().log("Data of ==>"+employee.getName());
                jsonBody= user.RegisterNewEmployee(employee);
                statusCode = 201;//created
            }
            else{
                throw new Exception("User already Exists");
            }


        }
        catch(Exception e)
        {
            context.getLogger().log("Opps error in saveEmployee method  ==>"+request.getBody()+"/n Error is "+e.getMessage());
            jsonBody = e.getMessage();
            statusCode = 400;//bad request
        }
        finally{
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent getEmployees(APIGatewayProxyRequestEvent request, Context context) //working fine
    {
        try{
            context.getLogger().log("Executing getEmployees method");
            List<Employee> employees = user.FindAllEmployee();
            jsonBody =  utility.convertListToString(employees,context);
            statusCode = 202;//Accepted
        }
        catch(Exception e)
        {
            context.getLogger().log("Opps error with ==>"+request.getPathParameters()+"/n Error is "+e.getMessage());
            jsonBody = e.getMessage();
            statusCode = 404; // Not found
        }
        finally{
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent getEmployeeById(APIGatewayProxyRequestEvent request, Context context) //working fine
    {
        try{

            context.getLogger().log("Executing getEmployeeByID method");
            String empId = request.getPathParameters().get("empId");
            Employee employee =  user.FindEmployeeById(empId)  ;
            if(employee!=null) {
                jsonBody = utility.convertObjToString(employee, context);
                context.getLogger().log("fetch employee By ID:::" + jsonBody);
                statusCode = 200;//ok
            }else{
                jsonBody = "Employee Not Found Exception : for Employee ID::" + empId;
                statusCode = 404;
            }
        }
        catch(Exception e)
        {
            context.getLogger().log("Error in getEmployeeByID method  ==>"+request.getPathParameters()+"/n Error is "+e.getMessage());
            jsonBody = e.getMessage();
            statusCode = 400;
        }
        finally{
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent deleteById(APIGatewayProxyRequestEvent request, Context context) //working fine
    {
        try{
            String empId = request.getPathParameters().get("empId");
            if(user.CheckIfExist(empId))
            {
                jsonBody = user.DeleteEmployeeById(empId);
                statusCode = 200;
            }
            else{
                throw new Exception("Employee does not exists");
            }

        }
        catch(Exception e)
        {
            context.getLogger().log("Opps error with  ==>"+request.getPathParameters()+"/n Error is "+e.getMessage());
            jsonBody = e.getMessage();
            statusCode = 400;
        }
        finally{
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public boolean validateInput(APIGatewayProxyRequestEvent request)
    {
        return true;
    }
    public APIGatewayProxyResponseEvent ValidationErrorResponse(String message){
        return utility.createAPIResponse(message,406,utility.createHeaders());//Not Acceptable
    }


}
