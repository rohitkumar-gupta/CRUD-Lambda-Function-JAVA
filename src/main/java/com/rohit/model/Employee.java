package com.rohit.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName="user")
public class Employee {
//    @DynamoDBHashKey(attributeName = "empId")
//    private String empId;
//    @DynamoDBAttribute(attributeName = "password")
//    private String password;
//    @DynamoDBAttribute(attributeName = "name")
//    private String name;
//    @DynamoDBAttribute(attributeName = "email")
//    private String email;
//    @DynamoDBAttribute(attributeName = "number")
//    private String number;


    @DynamoDBHashKey(attributeName = "empId")
    private String empId;

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "email")
    private String email;

    @DynamoDBAttribute(attributeName = "number")
    private String number;

    @DynamoDBAttribute(attributeName = "password")
    private String password;

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getPassword() {
        return password;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //    public void setEmpId(String empId){ this.empId =empId; }
//    public void setPassword(String password){ this.password =password; }
//    public void setName(String name){ this.name =name; }
//    public void setEmail(String email){ this.email =email; }
//    public void setNumber(String number){ this.number =number; }
//    public String getId(){ return empId;}
//    public String getPassword(){ return password;}
//    public String getName(){ return name;}
//    public String getEmail(){ return email;}
//    public String getNumber(){ return number;}









}
