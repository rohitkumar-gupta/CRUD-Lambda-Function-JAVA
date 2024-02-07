package com.rohit.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.rohit.model.Employee;

import java.util.List;

public class userDao {
    private DynamoDBMapper mapper;
    public  void initDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        mapper = new DynamoDBMapper(client);
    }
    public boolean CheckIfExist(String empId)
    {
        Employee employee = FindEmployeeById(empId);
        return employee != null;
    }
    public  String RegisterNewEmployee(Employee employee)
    {
        initDB();
        mapper.save(employee);
        return "Successfully Registered for "+employee.getName();
    }
    public List<Employee> FindAllEmployee()
    {
        initDB();
        return mapper.scan(Employee.class,new DynamoDBScanExpression());
    }
    public Employee FindEmployeeById(String empId)
    {
        initDB();
        return mapper.load(Employee.class,empId);
    }
    public String DeleteEmployeeById(String empId){
        initDB();
        Employee employee =  mapper.load(Employee.class,empId) ;
        mapper.delete(employee);
        return "Data deleted SuccessFully";

    }



}
