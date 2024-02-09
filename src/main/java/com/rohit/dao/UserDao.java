package com.rohit.dao;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

import com.rohit.model.Employee;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.util.List;

public class UserDao {
    static final Logger logger = LogManager.getLogger(UserDao.class);

    private DynamoDBMapper mapper;
    public  void initDB(){
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
        mapper = new DynamoDBMapper(client);
    }
    public boolean checkIfExist(String empId)
    {
        Employee employee = findEmployeeById(empId);
        return employee != null;
    }
    public  String registerNewEmployee(Employee employee)
    {
        initDB();
        mapper.save(employee);
        logger.info("User registered Successfully Named:: "+employee.getName());
        return "Successfully Registered for "+employee.getName();
    }
    public List<Employee> findAllEmployee()
    {
        initDB();
        logger.info("Getting all the user data");
        return mapper.scan(Employee.class,new DynamoDBScanExpression());
    }
    public Employee findEmployeeById(String empId)
    {
        initDB();
        logger.info("Data of Employee ID:: "+empId+" is fetching");
        Employee employee = mapper.load(Employee.class,empId);
        employee.setPassword("*****");
        return employee;
    }
    public String deleteEmployeeById(String empId){
        initDB();
        Employee employee =  mapper.load(Employee.class,empId) ;
        mapper.delete(employee);
        logger.warn("Data of "+employee.getName()+" is deleted");
        return "Data deleted SuccessFully";
    }
    public boolean validateCredentials(String empId,String password)
    {
        Employee employee =  findEmployeeById(empId);
        if(employee!=null)  return password.equals(employee.getPassword());
        return false;
    }
}
