package com.rohit.utility;

import com.rohit.model.Employee;

public class ValidatePayload {
    public boolean checkAll(Employee e)
    {
        boolean result;
        result = checkID(e.getEmpId())
                && checkEmail(e.getEmail())
                && checkName(e.getName())
                && checkPassword(e.getPassword())
                && checkNumber(e.getNumber());
        return result;
    }
    public boolean checkID(String empId)
    {
        return true;
    }
    public boolean checkPassword(String password)
    {
        return true;
    }
    public boolean checkNumber(String number)
    {
        return true;
    }
    public boolean checkName(String name)
    {
        return name.length()>4;
    }
    public boolean checkEmail(String empId)
    {
        return true;
    }
}
