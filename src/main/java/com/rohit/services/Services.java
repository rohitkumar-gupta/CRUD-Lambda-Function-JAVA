package com.rohit.services;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.rohit.utility.Utility;
import com.rohit.model.Employee;
import com.rohit.dao.UserDao;
import com.rohit.utility.ValidatePayload;
import org.apache.http.auth.InvalidCredentialsException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.util.List;


public class Services {
    static final Logger logger = LogManager.getLogger(Services.class);

    private String jsonBody = null;
    private int statusCode ;
    Utility utility = new Utility();
    //Creating an instance of mapper class
    UserDao user = new UserDao();
    public boolean checkForLogin(APIGatewayProxyRequestEvent request,Context context)
    {
        String url = request.getPath();
        logger.info("Accessing:: "+url);
        if(utility.validateLoginURL(url))
        {
            return true;
        }
        return false;
    }
    public APIGatewayProxyResponseEvent validateUser(APIGatewayProxyRequestEvent request,Context context)
    {
        try{
            logger.debug("Executing the validate user");
            Employee employee = utility.convertStringToObj(request.getBody(), context);
            if(user.validateCredentials(employee.getEmpId(), employee.getPassword()))
            {
                jsonBody = "Login SuccessFull";
                statusCode = 202;
                logger.info(jsonBody);
            }
            else{
                jsonBody = "UserId or Password is Invalid";
                statusCode = 401;
                throw new InvalidCredentialsException(jsonBody);
            }
        }
        catch(InvalidCredentialsException e)
        {
            logger.error(e.getMessage());
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
        }
        finally{
            logger.info("Completed execution of validateUser method");
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }

    public APIGatewayProxyResponseEvent saveEmployee(APIGatewayProxyRequestEvent request ,Context context) //working fine
    {
        try{
            logger.info("Executing saveEmployee method with data ==>"+request.getBody());
            Employee employee = utility.convertStringToObj(request.getBody(), context);
            //Check if ID already exists --> return true
            if(!user.checkIfExist(employee.getEmpId()))
            {
                jsonBody= user.registerNewEmployee(employee);
                statusCode = 201;//created
            }
            else{
                throw new Exception("User already Exists");
            }
        }
        catch(Exception e)
        {
            jsonBody = e.getMessage();
            logger.warn(jsonBody);
            statusCode = 400;//bad request
        }
        finally{
            logger.debug("Completed exectution of saveEmployee method");
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent getEmployees(APIGatewayProxyRequestEvent request, Context context) //working fine
    {
        try{
            logger.debug("Executing getEmployee method");
            List<Employee> employees = user.findAllEmployee();
            jsonBody =  utility.convertListToString(employees,context);
            statusCode = 202;//Accepted
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            jsonBody = e.getMessage();
            statusCode = 404; // Not found
        }
        finally{
            logger.debug("Completed execution of getEmployee method");
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent getEmployeeById(APIGatewayProxyRequestEvent request, Context context) //working fine
    {
        try{

            logger.debug("Executing getEmployeeByID method");
            String empId = request.getPathParameters().get("empId");
            Employee employee =  user.findEmployeeById(empId)  ;
            if(employee!=null) {
                jsonBody = utility.convertObjToString(employee, context);
                statusCode = 200;//ok
            }else{
                throw new Exception("User Not Found");
            }
        }
        catch(Exception e)
        {
            logger.error(e.getMessage());
            jsonBody = e.getMessage();
            statusCode = 404;
        }
        finally{
            logger.debug("Completed execution of getEmployeeById method");
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public APIGatewayProxyResponseEvent deleteById(APIGatewayProxyRequestEvent request, Context context) //working fine
    {
        try{
            String empId = request.getPathParameters().get("empId");
            logger.debug("Delete method is executing for EmpID::"+empId);
            if(user.checkIfExist(empId))
            {
                jsonBody = user.deleteEmployeeById(empId);
                statusCode = 200;
            }
            else{
                throw new Exception("Employee does not exists");
            }

        }
        catch(Exception e)
        {
            jsonBody = e.getMessage();
            logger.error(jsonBody);
            statusCode = 400;
        }
        finally{
            logger.debug("Completed execution of deleteEmployeeById method");
            return utility.createAPIResponse(jsonBody,statusCode,utility.createHeaders());
        }
    }
    public boolean validateInput(APIGatewayProxyRequestEvent request,Context context)
    {
        Employee employee = utility.convertStringToObj(request.getBody(), context);
        ValidatePayload input = new ValidatePayload();
        return input.checkAll(employee);
    }
    public APIGatewayProxyResponseEvent errorResponse(String message,int StatusCode){
        return utility.createAPIResponse(message,StatusCode,utility.createHeaders());//Not Acceptable
    }


}
